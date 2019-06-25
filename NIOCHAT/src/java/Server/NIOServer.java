package Server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @describe: 入口处
 * @author:liyuanwen
 * @date: 2019/6/14 19:59
 **/
public class NIOServer implements Runnable{


    private final static int BUF_SIZE = 1024;
    private final static int TIMEOUT = 3000;
    private final static int PORT = 8080;

    /**
     * 存放客户端socketChannel
     **/
    private final Map<String, SocketChannel>  clients = new HashMap<String, SocketChannel>();

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private ByteBuffer readBuffer = null;
    private ByteBuffer writeBuffer = null;

    public NIOServer() throws IOException {
       startUp();
    }

    public void startUp() throws IOException {
        //初始化Selector和ServerSocketChannel
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //配置端口号
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        //注册ACCEPT事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //当事件来临时会运行Acceptor线程
        selectionKey.attach(new Acceptor(serverSocketChannel,selector,clients));
        System.out.println(Thread.currentThread()+"服务器启动成功");
        System.out.println("--------------------------------------------------------------------");
    }

    /**
     *  事件分发
     **/
    public void dispatch(SelectionKey key){
        if (!key.isValid()){
            System.out.println("key无效");
            return;
        }
        Runnable runnable = (Runnable) key.attachment();
        if (runnable!=null){
            System.out.println("事件处理");
            //运行对应的事件处理器线程
//            new Thread(runnable).start();
            runnable.run();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                int selected = selector.select(TIMEOUT);
                System.out.println("selected->"+selected);
                if (selected == 0){
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                while (it.hasNext()){
                    //分发处理
                    dispatch((SelectionKey)it.next());
                    //删除
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (selector!=null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocketChannel!=null){
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        new NIOServer().run();
    }
}
