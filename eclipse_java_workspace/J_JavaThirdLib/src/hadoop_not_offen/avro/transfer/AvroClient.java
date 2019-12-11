package hadoop_not_offen.avro.transfer;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor; 

import java.net.InetSocketAddress;


public class AvroClient {
/*
 * 
#转换 .avdl 文件到 .avpr 文件用
java -jar avro-tools-1.8.2.jar idl DemoService.avdl DemoService.avpr
java -jar avro-tools-1.8.2.jar compile protocol  DemoService.avpr  .

 */
    public static void main(String[] args) throws Exception {

//        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(65111));
      //二选 一
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
        System.out.print("avro " + max + " 次RPC调用，耗时：" + elapse + "毫秒，平均" + perform + "次/秒");


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