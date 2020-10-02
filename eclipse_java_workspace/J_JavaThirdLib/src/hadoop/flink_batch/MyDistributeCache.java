package hadoop.flink_batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;


public class MyDistributeCache {
 
	public static void main(String[] args) throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//		env.registerCachedFile("hdfs:///path/to/your/file", "hdfsFile");
		env.registerCachedFile("file:///etc/fstab", "localExecFile", false);//���һ�����Ƿ��ִ��
		DataSource<String> text = env.fromElements("11","22");
		DataSet<String> input =  text.map(new MyMapper());
		input.print();
//		env.execute("MyDistributeCacheTest");
	}
}  

  class MyMapper extends RichMapFunction<String, String> {
    @Override
    public void open(Configuration config) {
      File myFile = getRuntimeContext().getDistributedCache().getFile("localExecFile");
      System.out.println("�õ������ļ���СΪ"+myFile.getUsableSpace()+"����Ϊ:");
      Scanner scanner;
      try {
		scanner = new Scanner( new FileInputStream(myFile));
		 while(scanner.hasNextLine())
	     {
	      	String line=scanner.nextLine();
	      	System.out.println(line);
	     }
      } catch (FileNotFoundException e) {
			e.printStackTrace();
      }
    }
    @Override
    public String map(String value) throws Exception {
     return value;
    }
}