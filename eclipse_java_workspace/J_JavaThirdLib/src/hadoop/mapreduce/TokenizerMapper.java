package hadoop.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object,Text,Text,IntWritable>
{
	enum Count{
		Error
	}
	private final static IntWritable one=new IntWritable(1);
	private Text word=new Text();
	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {//value ��һ�е�����
		StringTokenizer token=new StringTokenizer(value.toString());
		while(token.hasMoreElements())
		{
			try{
				word.set(token.nextToken());
				context.write(word, one);//��ӦMapper������ģ��
			}catch(Exception e)
			{
				context.getCounter(Count.Error)//�Լ������enum
								.increment(1);
			}
		}
	}
}
 