package hadoop.flink_stream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment; 
import org.apache.flink.util.Collector;

  
public class MyFilter {
	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<Integer>  text = env.fromElements(new Integer[] {1,3,4,5,0,-3,-5});
		DataStream<Integer> counts = text.filter(new FilterFunction<Integer>() {
			@Override
			public boolean filter(Integer value) throws Exception {
				return value>0;
			}
		}) ;
		counts.print(); 
		env.execute("FilterFunctionTest");
	} 
}  