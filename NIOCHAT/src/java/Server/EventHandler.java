package Server;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @describe: 处理读写请求
 * @author:liyuanwen
 * @date: 2019/6/15 10:46
 **/
public class EventHandler implements Runnable{

    private static final int BUF_SIZE = 2048;

    int count = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(count,count*2,0,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024),new ThreadPoolExecutor.DiscardOldestPolicy());

    private Charset charset =Charset.forName("UTF-8");

    private final SocketChannel socketChannel;
    private final Selector selector;
    private final SelectionKey selectionKey;
    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer ;
    private final Map<String,SocketChannel> clients;
    private String targetClient = "ALL";

    EventHandler(SocketChannel channel, Selector selector, Map<String,SocketChannel> clients) throws IOException {
        this.socketChannel = channel;
        this.selector = selector;
        this.clients = clients;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
    }

    public void read() throws IOException {
        System.out.println("开始读取客户端发来的数据");
        readBuffer = ByteBuffer.allocate(BUF_SIZE);
        readBuffer.clear();
        int readByte = socketChannel.read(readBuffer);
        String clientName  = getClientName(socketChannel);
        //客户端关闭，则删除clients中的记录，关闭channel
        if (readByte<0){
            if (clients.containsKey(clientName)){
                clients.remove(clientName);
            }
            socketChannel.close();
            selectionKey.cancel();
            return;
        }
        System.out.println("readByte : "+readByte);
        //读取完整数据
        while(readByte>0){
            //
            readBuffer.flip();
            while (readBuffer.hasRemaining()){
                System.out.print((char)readBuffer.get());
            }
            readBuffer.compact();
            readByte = socketChannel.read(readBuffer);
        }
        System.out.println();
        //转换监听事件
        //selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    /**
     * 将ByteBuffer 转换成 String
     **/
    public String convertString() throws CharacterCodingException {
        CharsetDecoder decoder = null;
        CharBuffer buf = null;
        decoder = charset.newDecoder();

//        buf = decoder.decode(readBuffer.asReadOnlyBuffer());
        buf = decoder.decode(readBuffer);//用这个只能转一次
        return buf.toString();
    }

    /**
     * 解析数据
     **/
    public boolean process() throws CharacterCodingException {
        String msg = convertString().trim();
        System.out.println("msg->"+msg);
        String[] result = msg.split(":");
        int length = result.length;
        System.out.println("解析数据后长度-> "+length);
        if (length!=1){
            //发送客户端名称
            String sendClient = result[0];
            //目标客户端名称
            targetClient = result[1];
            //内容
            String content = result[2];
            StringBuilder sb = new StringBuilder();
            sb.append(sendClient);sb.append(" 对 ");sb.append(targetClient);sb.append(" 说: ");
            sb.append(content);
            System.out.println(sb.toString());
            writeBuffer = ByteBuffer.allocate(sb.toString().getBytes().length);
            writeBuffer.put(sb.toString().getBytes());
            writeBuffer.flip();
            return true;
        }
        return false;
    }


    public void write() throws IOException {
        boolean b = process();
        if (b){
            System.out.println("返回结果");
            SocketChannel sc = null;
            Set<Map.Entry<String,SocketChannel>> set = clients.entrySet();
            Iterator<Map.Entry<String,SocketChannel>> it = set.iterator();
            if ("ALL".equals(targetClient)){
                while(it.hasNext()){
                    sc = it.next().getValue();
                    while (writeBuffer.hasRemaining()){
                        sc.write(writeBuffer);
                    }
                    writeBuffer.compact();
                }
            }else{
                while (it.hasNext()){
                    Map.Entry<String,SocketChannel> temp = it.next();
                    if (targetClient.equals(temp.getKey())){
                        sc = temp.getValue();
                        while (writeBuffer.hasRemaining()){
                            sc.write(writeBuffer.asReadOnlyBuffer());
                            socketChannel.write(writeBuffer);
                        }
                        writeBuffer.compact();
                        break;
                    }
                }
            }

        }
//        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    /**
     * 返回客户端名称
     **/
    public String getClientName(SocketChannel channel){
        Socket socket = channel.socket();
        return "["+ socket.getInetAddress().toString().substring(1)
                +":"+Integer.toHexString(socket.hashCode())+"]";
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"运行读写线程");
        if (selectionKey.isReadable() && selectionKey.isValid()) {
            executor.submit(() -> {
                try {
                    read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (selectionKey.isWritable() || selectionKey.isValid()) {
            executor.submit(() -> {
                try {
                    write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
