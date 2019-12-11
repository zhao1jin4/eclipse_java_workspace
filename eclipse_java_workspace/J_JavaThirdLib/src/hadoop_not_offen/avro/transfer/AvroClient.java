package hadoop_not_offen.avro.transfer;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor; 

import java.net.InetSocketAddress;


public class AvroClient {
/*
 * 
#ת�� .avdl �ļ��� .avpr �ļ���
java -jar avro-tools-1.8.2.jar idl DemoService.avdl DemoService.avpr
java -jar avro-tools-1.8.2.jar compile protocol  DemoService.avpr  .

 */
    public static void main(String[] args) throws Exception {

//        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(65111));
      //��ѡ һ
        SaslSocketTransceiver client = new SaslSocketTransceiver(new InetSocketAddress(10000));
        
        DemoService proxy = (DemoService) SpecificRequestor.getClient(DemoService.class, client);
        System.out.println(proxy.ping());

        int max = 100000;
        Long start = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            call(proxy);
        }

        Long end = System.currentTimeMillis();
        Long elapse = end - start;
        int perform = Double.valueOf(max / (elapse / 1000d)).intValue();
        System.out.print("avro " + max + " ��RPC���ã���ʱ��" + elapse + "���룬ƽ��" + perform + "��/��");


        // cleanup
        client.close();
    }

    private static void call(DemoService proxy) throws AvroRemoteException {
        //client.ping();
        //System.out.println("ping()=>" + client.ping());

        QueryParameter parameter = new QueryParameter();
        parameter.setAgeStart(5);
        parameter.setAgeEnd(50);

        proxy.getPersonList(parameter);
        //System.out.println(client.getPersonList(parameter));
    }
}