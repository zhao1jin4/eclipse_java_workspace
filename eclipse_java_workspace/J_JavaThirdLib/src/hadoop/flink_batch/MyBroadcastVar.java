package hadoop.flink_batch;

import java.util.Collection;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.configuration.Configuration;


public class MyBroadcastVar {
 
	public static void main(String[] args) throws Exception {
		final  ExecutionEnvironment env =  ExecutionEnvironment.getExecutionEnvironment();
		// 1. The DataSet to be broadcast
		DataSet<Integer> toBroadcast = env.fromElements(1, 2, 3);
		DataSet<String> data = env.fromElements("a", "b");
		MapOperator<String, String> result = data.map(new RichMapFunction<String, String>() {
		    @Override
		    public void open(Configuration parameters) throws Exception {
		      // 3. Access the broadcast DataSet as a Collection
		      Collection<Integer> broadcastSet = getRuntimeContext().getBroadcastVariable("broadcastSetName");
		      System.out.println("取到广播"+broadcastSet);
		    }
		    @Override
		    public String map(String value) throws Exception {
		        return value;
		    }
		}).withBroadcastSet(toBroadcast, "broadcastSetName"); // 2. Broadcast the DataSet
		//广播出去的变量存在每个taskManager的内存中，不应过大
		result.print(); 
//		env.execute("MyBroadcastVarTest");
	}
	 
}  
 