package Server;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * @describe: 处理客户端连接请求
 * @author:liyuanwen
 * @date: 2019/6/14 20:06
 **/
public class Acceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector;
    private Map<String, SocketChannel> clients;
    //发送给客户端的名单
    private StringBuilder clientNameList = new StringBuilder();

    Acceptor(ServerSocketChannel channel, Selector selector, Map<String, SocketChannel> clients){
        this.serverSocketChannel = channel;
        this.selector = selector;
        this.clients = clients;
        clientNameList.append("ClientList:ALL:");
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel!=null){
                System.out.println("服务端收到一个客户端连接.........");
                //非阻塞
                socketChannel.configureBlocking(false);
                String clientName = getAutoClientName(socketChannel);
                clients.put(clientName,socketChannel);
                clientNameList.append(clientName+":");
                System.out.println("广播");
                broadcast();
                new EventHandler(socketChannel,selector,clients);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播
     **/
    public void broadcast() throws IOException {
        ByteBuffer buffer = null;
        for (SocketChannel sc : clients.values()){
            buffer = ByteBuffer.allocate(clientNameList.toString().getBytes().length);
            buffer.put(clientNameList.toString().getBytes());
            buffer.flip();
            while (buffer.hasRemaining()){
                sc.write(buffer);
            }
        }
    }

    /**
     * 生成客户端名称
     **/
    public String getAutoClientName(SocketChannel channel){
        Socket socket = channel.socket();
        return "["+ socket.getInetAddress().toString().substring(1)
                +"|"+Integer.toHexString(socket.hashCode())+"]";
    }
}
