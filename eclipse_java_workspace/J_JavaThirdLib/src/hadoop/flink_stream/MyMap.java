package hadoop.flink_stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment; 
import org.apache.flink.util.Collector;

 
 
//报找不到 org.apache.flink.runtime.state.CheckpointListener 在运行环境里，
//如想IDE开发，引用的 flink-1.9.1/lib/flink-dist_2.12-1.9.1.jar
		
public class MyMap {
 
	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		Tuple2<Integer,Integer> num1=	new Tuple2<>(1,2) ;
		Tuple2<Integer,Integer> num2=	new Tuple2<>(3,5) ;
		Tuple2<Integer,Integer>  [] array=new Tuple2 [] {num1,num2} ;
		DataStream<Tuple2<Integer,Integer>>  text = env.fromElements(array);
		DataStream<Integer> counts = text.map(new Plus()) ;
		counts.print(); 
		env.execute("MapFunctionTest");
	}
	public static final class Plus implements  MapFunction<Tuple2<Integer, Integer>,Integer> {
		@Override
		public Integer  map(Tuple2<Integer, Integer> value) throws Exception {
			return   value.f0+value.f1;
		}
	}
}  