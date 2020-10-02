package hadoop.flink_stream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;

import hadoop.flink_stream.MyStreamToKafka.WordWithCount;

//flink-core-1.9.0.jar  
//flink-streaming-java_2.12-1.9.0.jar
//flink-java-1.9.0.jar 

//����IDE���������õ� flink-1.9.1/lib/flink-dist_2.12-1.9.1.jar

public class MyStreamToKafka {
//	flink-connector-kafka-0.11_2.12-1.9.1.jar
//	������� 	flink-connector-kafka-base_2.12-1.9.1.jar ,flink-runtime_2.12-1.9.1.jar
	
	public static void main(String[] args) throws Exception {
	    // https://ci.apache.org/projects/flink/flink-docs-release-1.9/getting-started/tutorials/datastream_api.html β
		//IDE��������û��Ч�� ??? ��ԭ�������kafak�汾ҪΪ0.11����,����Ҫ������flink cluster ������
		final int port;
        try {
            final ParameterTool params = ParameterTool.fromArgs(args);
            port = params.getInt("port");
        } catch (Exception e) {
            System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'");
            return;
        }
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //�Զ�������Դʹ�� StreamExecutionEnvironment.addSource(function)//ʵ��SourceFunction�ӿڣ��� extends RichSourceFunction��
        //env.addSource(new FlinkKafkaConsumer011<>(topic,deserializer,props));
        DataStream<String> text = env.socketTextStream("localhost", port, "\n");
        DataStream<WordWithCount> windowCounts = text
       .flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> out) {
                for (String word : value.split("\\s")) {
                    out.collect(new WordWithCount(word, 1L));
                }
            }
        })
       .keyBy("word")
       .timeWindow(Time.seconds(5), Time.seconds(1))
       .reduce(new ReduceFunction<WordWithCount>() {
           @Override
           public WordWithCount reduce(WordWithCount a, WordWithCount b) {
               return new WordWithCount(a.word, a.count + b.count);
           }
       });
       windowCounts.map( new MapFunction<WordWithCount , String>() {
           @Override
           public String map( WordWithCount  value) {
               return value.toString();
           }
       })
      //�Զ����������Sink����StreamExecutionEnvironment.addSink(function)//ʵ��SinkFunction�ӿڣ��� extends RichSinkFunction��
       .addSink(new FlinkKafkaProducer011<>("localhost:9092", "wiki-result", new SimpleStringSchema()));
        env.execute("Flink Connect to Kafka Test");
    }

    public static class WordWithCount {

        public String word;
        public long count;

        public WordWithCount() {}

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }
}
