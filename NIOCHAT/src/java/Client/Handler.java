package Client;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @describe: 处理客户端注册的事件
 * @author:liyuanwen
 * @date: 2019/6/15 16:48
 **/
public class Handler implements Runnable{

    private static final int BUF_SIZE = 2048;

    private final NIOClient client;

    private final SocketChannel socketChannel;
    private final Selector selector;
    private final SelectionKey selectionKey;

    private ByteBuffer readBuf = ByteBuffer.allocate(BUF_SIZE);
    private ByteBuffer writeBuf;
    private Charset charset = Charset.forName("UTF-8");

    public Handler(SocketChannel channel, Selector selector,NIOClient client) throws IOException, InterruptedException {
        socketChannel = channel;
        this.selector = selector;
        this.client = client;
        //注册read事件
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Handler线程初始化");
    }


    public void read() throws IOException {
        System.out.println("Handler线程读数据");
        readBuf.clear();
        int readSize = socketChannel.read(readBuf);
        //服务器关闭
        if (readSize<0){
            socketChannel.close();
            return;
        }
        System.out.println("读取到字节："+readSize);
        while(readSize>0){
            readBuf.flip();
            while (readBuf.hasRemaining()){
                System.out.print(charset.decode(readBuf));
            }
            readBuf.compact();
            readSize = socketChannel.read(readBuf);
        }
        System.out.println();
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    public void process() throws CharacterCodingException {
        System.out.println("处理数据中");
        //返回给服务器的信息
        String info = "";
        String content = convertString().trim();
        if (content.startsWith("ClientList")){
            int contLength = content.length();
            String str = content.substring(11,contLength-1);
            String[] clients = str.split(":");
            if (client.getName()==null){
                client.setName(clients[clients.length-1]);
            }
            Arrays.asList(clients).stream().forEach(name -> {
                if (!"".equals(name)){
                    client.add(name);
                }
            });
            info = "OK";
        }else{
            info = "OK";
        }
        System.out.println("发送数据为-> " + info);
        writeBuf = ByteBuffer.allocate(info.getBytes().length);
        writeBuf.clear();
        writeBuf.put(info.getBytes());
        writeBuf.flip();
    }

    public void write() throws IOException {
        process();
        System.out.println("返回结果");
        while (writeBuf.hasRemaining()){
            socketChannel.write(writeBuf);
        }
        writeBuf.compact();
        selectionKey.interestOps(SelectionKey.OP_READ);
    }



    /**
     * 将ByteBuffer 转换成 String
     * 注意：未满ByteBuffer长度的数据，为以""填充，使用时，调用trim()方法
     **/
    public String convertString() throws CharacterCodingException {
        CharsetDecoder decoder = null;
        CharBuffer buf = null;
        decoder = charset.newDecoder();

        buf = decoder.decode(readBuf);

        return buf.toString();
    }

    @Override
    public void run() {
        while (true){
            try {
                int select = selector.select();
                if (select>0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while (it.hasNext()){
                        SelectionKey key = it.next();
                        if (key.isReadable()){
                            read();
                        }else if(key.isWritable()){
                            write();
                        }
                        it.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
