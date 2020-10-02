package zeromq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZContext;

/**
 * Pubsub envelope publisher
 */

public class psenvpub
{

    public static void main(String[] args) throws Exception
    {
        // Prepare our context and publisher
        try (ZContext context = new ZContext()) {
            Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5563");

            while (!Thread.currentThread().isInterrupted()) {//一直发
                // Write two messages, each with an envelope and content
                publisher.sendMore("A");//相当于标题
                publisher.send("We don't want to see this");//相当于内容，要和标题一起发送
                publisher.sendMore("B1");
                publisher.send("We would like to see this");
            }
        }
    }
}