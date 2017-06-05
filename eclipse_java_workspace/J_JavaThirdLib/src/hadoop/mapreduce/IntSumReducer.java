package hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public    class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable>
{
	private final static IntWritable result=new IntWritable(1);

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {

		int sum=0;
		for(IntWritable value:values)
		{
			
System.out.println("in Reducer---"+value);
			sum+=value.get();
		}
		result.set(sum);
		context.write(key, result);
	}
	 
	
}