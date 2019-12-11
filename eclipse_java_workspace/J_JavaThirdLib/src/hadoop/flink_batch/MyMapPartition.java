package hadoop.flink_batch;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;


public class MyMapPartition {
 
	public static void main(String[] args) throws Exception {
		final  ExecutionEnvironment env =  ExecutionEnvironment.getExecutionEnvironment();
//		DataSource<String>  text = env.fromElements(WordCountData.WORDS);
		//------
		DataSet<Long> numbers = env.generateSequence(1, 100);
		DataSet<String> text = numbers.map(new MapFunction<Long,String>(){
			@Override
			public String map(Long value) throws Exception {
				return value+"'";
			}
		}); 
		 DataSet< Long> mapPartition = text.mapPartition(new MapPartitionFunction<String, Long>() {
			  public void mapPartition(Iterable<String> values, Collector<Long> out) {
			    long c = 0;
			    for (String s : values) {
			      c++;
			    }
			    out.collect(c);
			  }
			});
		 mapPartition.print();
		//env.execute("MyMapPartitionFunctionTest");
	} 
}  
 