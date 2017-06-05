package hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MainMapReduce2 {

	 
	// bin/hdfs dfs -rm -r -f /user/zhaojin/myout
	//eclipse 带参数 hdfs://127.0.0.1:9000/user/zhaojin/myin hdfs://127.0.0.1:9000/user/zhaojin/myout
	// hadoop jar xx.jar myin myout
	public static void main(String[] args) throws Exception
	{
		Configuration conf=new Configuration();
		//---
		String[] otherArgs=new GenericOptionsParser(conf, args).getRemainingArgs();
		if(otherArgs.length!=2)
		{
			System.err.println("Usage world count <in> <out>");
			System.exit(2);
		}
		Job job= Job.getInstance(conf,"world count");//Job名字
		job.setJarByClass(MainMapReduce2.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);//--
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);//要与Mapper后两个模板对应
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
	
} 
 