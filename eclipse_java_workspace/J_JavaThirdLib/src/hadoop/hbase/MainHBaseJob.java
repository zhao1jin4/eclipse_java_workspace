package hadoop.hbase;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
 

public class MainHBaseJob {
	public static void main(String[] args) throws Exception
	{
		//org.apache.hadoop.hbase.CompatibilityFactory x;
		///home/zhaojin/hbase-0.98.3-hadoop2/lib/hbase-hadoop-compat-0.98.3-hadoop2.jar
		
		//要在linux上运行
		//hbase shell
		//>create 'myTable','cf
		//>put 'myTable', 'row1', 'cf:a', 'value1'
		//javac MapReduce.java -d . -Djava.ext.dirs=/home/zhaojin/hbase-0.98.3-hadoop2/lib/
		//java -Djava.ext.dirs=/home/zhaojin/hbase-0.98.3-hadoop2/lib/ hadoop.hbase.MainHBaseJob
		Configuration config = HBaseConfiguration.create();
		
		Job job =     Job.getInstance(config, "ExampleRead");
	//			job.setJarByClass(MyReadJob.class);     // class that contains mapper
			
		Scan scan = new Scan();
		scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
		scan.setCacheBlocks(false);  // don't set to true for MR jobs
		scan.addColumn(Bytes.toBytes("cf"),Bytes.toBytes("attr1"));
//		scan.setStartRow( Bytes.toBytes("row9"));                   // start key is inclusive
//		scan.setStopRow( Bytes.toBytes("row" +  (char)8));  // stop key is exclusive
		
		TableMapReduceUtil.initTableMapperJob(
		  "myTable",        // input HBase table name
		  scan,             // Scan instance to control CF and attribute selection
		  MyMapper.class,   // mapper
		  Text.class,         // mapper output key
		  IntWritable.class,  // mapper output value
		  job);
  
		//-------方式一
		job.setReducerClass(MyTableReducer.class);    // reducer class
		job.setNumReduceTasks(1);    // at least one, adjust as required
		FileOutputFormat.setOutputPath(job, new Path("/tmp/mr/mySummaryFile"));  // adjust directories as required
		//-------方式二
//		TableMapReduceUtil.initTableReducerJob(
//		  "myTable",    
//		  MyTableReducer.class,  
//		  job);
		
		job.setOutputFormatClass(NullOutputFormat.class);   // because we aren't emitting anything from mapper
		job.setNumReduceTasks(0);
		
		boolean b = job.waitForCompletion(true);
		if (!b) {
		  throw new IOException("error with job!");
		}   
	}
}
 class MyTableReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable>  //<KEYIN,VALUEIN,KEYOUT>
 {
 	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    		int i = 0;
    		for (IntWritable val : values) {
    			i += val.get();
    		}
    		Put put = new Put(Bytes.toBytes(key.toString()));
    		put.add(Bytes.toBytes("cf"), Bytes.toBytes("count"), Bytes.toBytes(i));
    		context.write(null, put);
   	}
}
  class MyMapper extends TableMapper<Text, IntWritable>  //<KEYOUT,VALUEOUT>
  {
		private final IntWritable ONE = new IntWritable(1);
	   	private Text text = new Text();
	   	public void map(ImmutableBytesWritable row, Result value, Context context) 
	   			throws IOException, InterruptedException 
	   	{
	        	String val = new String(value.getValue(Bytes.toBytes("cf"), Bytes.toBytes("attr1")));
	          	text.set(val);     // we can only emit Writables...
	        	context.write(text, ONE);
	        	System.out.println("====value:"+value+",row===="+row);
	   	}
	}
 