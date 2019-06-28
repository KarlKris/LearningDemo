package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @describe: 聊天客户端
 * @author:liyuanwen
 * @date: 2019/6/14 17:58
 **/
public class NIOClient {

    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8080;
    private static final String URL = "127.0.0.1";

    //名单
    private HashSet<String> list = new HashSet<>();
    private String name;

    private  SocketChannel socketChannel;
    private  Selector selector;

    public NIOClient() throws IOException, InterruptedException {
        init();
    }

    public void init() throws IOException, InterruptedException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        //非阻塞
        socketChannel.configureBlocking(false);
        //连接服务器
        socketChannel.connect(new InetSocketAddress(URL,PORT));
        //成功连接
        if (socketChannel.finishConnect()){
            System.out.println("成功连接");
            new Thread(new Handler(socketChannel,selector,this)).start();
        }

    }

    public void send(String content) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(":");
        sb.append(getTargetClient());
        sb.append(":");
        sb.append(content);
        System.out.println(sb.toString());
        ByteBuffer writeBuf = ByteBuffer.allocate(sb.toString().getBytes().length);
        writeBuf.put(sb.toString().getBytes());
        writeBuf.flip();
        while (writeBuf.hasRemaining()){
            socketChannel.write(writeBuf);
        }
    }

    public String getTargetClient(){
        String res = null;
        int length = list.size();
        System.out.println("list.size() - > "+length);
        Random random = new Random();
        int index= random.nextInt(length);
        System.out.println("randomNum - > "+index);
        Iterator<String> it = list.iterator();
        int i = 1;
        while (it.hasNext()){
            if (i==2){
                String str = it.next();
                System.out.println("targetClientName - > "+str);
                res = name.equals(str) ?  "ALL" : str;
                break;
            }
            it.next();
            i++;
        }
        return res==null ? "ALL" : res;
    }

    public void add(String clientName) {
        this.list.add(clientName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NIOClient client = new NIOClient();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("-------------------------------------------------------");
        client.send("holle , I am one of the client, my name is "+client.getName());
    }
}
