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

            while (!Thread.currentThread().isInterrupted()) {//һֱ��
                // Write two messages, each with an envelope and content
                publisher.sendMore("A");//�൱�ڱ���
                publisher.send("We don't want to see this");//�൱�����ݣ�Ҫ�ͱ���һ����
                publisher.sendMore("B1");
                publisher.send("We would like to see this");
            }
        }
    }
}