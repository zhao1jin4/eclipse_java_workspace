package hadoop.flink_batch;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.ReduceOperator;

import hadoop.flink_batch.MyGroupBy.WC;


public class MyGroupBy {
 
	public static void main(String[] args) throws Exception {
		final  ExecutionEnvironment env =  ExecutionEnvironment.getExecutionEnvironment();
		//DataSource<WC>  worlds = env.fromElements(new WC[] {new WC("hello",1),new WC("world",1),new WC("world",2)});
		DataSource<WC>  worlds = env.fromElements( new WC("hello",1),new WC("world",1),new WC("world",2) );
		ReduceOperator<WC> reduce = worlds.groupBy( new KeySelector<MyGroupBy.WC, String>() {
			@Override
			public String getKey(WC value) throws Exception {
				return value.word;
			}
		})
		.reduce(new ReduceFunction<MyGroupBy.WC>() {
			@Override
			public WC reduce(WC w1, WC w2) throws Exception {
				return new WC(w2.word,w1.frequency+w2.frequency);
			}
		});
		reduce.print();
//		env.execute("MyGroupByFunctionTest");
	} 
	
	
	public static class WC {
		public String word;
		public long frequency;
		
		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public long getFrequency() {
			return frequency;
		}

		public void setFrequency(long frequency) {
			this.frequency = frequency;
		}
		public WC(String word, long frequency) {
			super();
			this.word = word;
			this.frequency = frequency;
		}
		@Override
		public String toString() {
			return "WC [word=" + word + ", frequency=" + frequency + "]";
		}
	}
}  
 