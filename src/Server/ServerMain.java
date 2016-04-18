package Server;

import Common.User.UserInfoMessage;
import Server.ReceiveHandle.ServerReceiveHandler;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 *
 * Created by Administrator on 2016/4/16.
 */

public class ServerMain {
    private Selector selector = null;
    static final int PORT = 8080;
    private ServerSocketChannel server = null;
    private Charset charset = Charset.forName("UTF-8");

    UserInfoMessage userInfoMessage = new UserInfoMessage();//用户信息管理

    public void init() throws Exception{
        selector = Selector.open();
        server = ServerSocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress(PORT);
        server.bind(isa);//服务器channel绑定端口
        server.configureBlocking(false);//以非阻塞式工作
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void execute() throws Exception{
        int len;
        while ( (len = selector.select()) > 0) {
            System.out.println(len);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();

                if (sk.isAcceptable()) {
                    SocketChannel sc = server.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                    sk.interestOps(SelectionKey.OP_ACCEPT);
                }

                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    StringBuilder content = new StringBuilder();
                    int recLen = sc.read(buff);

                    //连接已经断开
                    if (recLen < 0) {
                        sk.cancel();
                        if (sk.channel() != null) {
                            sk.channel().close();
                            continue;
                        }
                    }

                    while (recLen > 0) {
                        buff.flip();
                        content.append(new String(buff.array()));
                        recLen = sc.read(buff);
                    }
                    //接收数据处理
                    ServerReceiveHandler handler = new ServerReceiveHandler(new String(content),userInfoMessage,sc);
                    handler.handle();
                }

                iterator.remove();
            }
        }
    }


    public static void main(String[] args) throws Exception{
        ServerMain server = new ServerMain();
        server.init();
        server.execute();

    }

}
