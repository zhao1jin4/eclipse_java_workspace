package hadoop.spark;
 
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

public class SimpleApp {
  public static void main(String[] args) {
    
	//String logFile = "/home/dell/spark-1.0.0-bin-hadoop2/README.md";
	 String logFile = "H:\\java_program\\spark-2.4.3-bin-hadoop2.7\\spark-2.4.3-bin-hadoop2.7/README.md";
	  //�ļ�����hdfs://,URI,     ֧��ͨ���,ѹ�� ��/my/directory/*.gz"
	SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]");
	
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> logData = sc.textFile(logFile).cache();//textFile�����ڶ��������ɼ����÷ָ��ļ�����,������С���С,HDFSĬ����64M

    long numAs = logData.filter(s -> s.contains("a")).count();

//    long numAsJdk8 = logData.filter((str)->str.contains("a")).count();//JDK8 �汾
    
    long numBs = logData.filter(s -> s.contains("b")).count();

    
    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    
   //---����δ���� 
   
    JavaPairRDD<String,String> dirData=sc.wholeTextFiles("dir"); //(filename, content) pairs
   // sc.sequenceFile(path, keyClass, valueClass); //��Ӧ�� mapreduce �� SequenceFileInputFormat
  //  sc.hadoopRDD(conf, inputFormatClass, keyClass, valueClass)
  //  sc.newAPIHadoopRDD(conf, fClass, kClass, vClass)
    
    logData.map(s -> s.length()).reduce((a, b) -> a + b);
    
    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
    JavaRDD<Integer> distData = sc.parallelize(data);
    distData.reduce((a, b) -> a + b) ;
    distData.persist(StorageLevel.MEMORY_AND_DISK());
    
    Tuple2<String,Object> x=  new Tuple2<String,Object>("myKey", new Object()) ;
    String key=x._1();
    Object value= x._2();
    
    JavaRDD<String> lines = sc.textFile("data.txt");
    JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2<String, Integer>(s, 1));//�������������͵��� Tuple2
    JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
    counts.sortByKey();
     
  }
}
