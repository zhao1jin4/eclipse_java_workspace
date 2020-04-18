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
	  //文件可是hdfs://,URI,     支持通配符,压缩 如/my/directory/*.gz"
	SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]");
	
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> logData = sc.textFile(logFile).cache();//textFile方法第二个参数可加配置分割文件数量,但不能小块大小,HDFS默认是64M

    long numAs = logData.filter(s -> s.contains("a")).count();

//    long numAsJdk8 = logData.filter((str)->str.contains("a")).count();//JDK8 版本
    
    long numBs = logData.filter(s -> s.contains("b")).count();

    
    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
    
   //---以下未测试 
   
    JavaPairRDD<String,String> dirData=sc.wholeTextFiles("dir"); //(filename, content) pairs
   // sc.sequenceFile(path, keyClass, valueClass); //对应于 mapreduce 的 SequenceFileInputFormat
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
    JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2<String, Integer>(s, 1));//返回是两个类型的用 Tuple2
    JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
    counts.sortByKey();
     
  }
}
