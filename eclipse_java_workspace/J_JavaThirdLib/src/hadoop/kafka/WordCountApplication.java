package hadoop.kafka;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.state.KeyValueStore;
 
public class WordCountApplication {
 
    public static void main(final String[] args) throws Exception {
//    	org.rocksdb.RocksDBException  x;
    	//Òª rocksdbjni-5.7.3.jar
    	
    	//kafka-topics  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TextLinesTopic
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
 
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream("TextLinesTopic");//<key ,value>¶ÁÈëtopic
       
//        JDK 8
/*
        KTable<String, Long> wordCounts = textLines
            .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
            .groupBy((key, word) -> word)
            .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
*/      
        
        //JDK 7
        KTable<String, Long> wordCounts = textLines
                .flatMapValues((ValueMapper<String, Iterable<String>>) textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
        
        
        wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));//Êä³ötopic
 
        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }
    /* 
    public static void demo() throws Exception  
    {
    	// Serializers/deserializers (serde) for String and Long types
    	final Serde<String> stringSerde = Serdes.String();
    	final Serde<Long> longSerde = Serdes.Long();
    	 
    	// Construct a `KStream` from the input topic "streams-plaintext-input", where message values
    	// represent lines of text (for the sake of this example, we ignore whatever may be stored
    	// in the message keys).
    	 StreamsBuilder builder = new StreamsBuilder();
    	KStream<String, String> textLines = builder.stream("streams-plaintext-input",
    	    Consumed.with(stringSerde, stringSerde));
    	 
    	KTable<String, Long> wordCounts = textLines
    	    // Split each text line, by whitespace, into words.
    	    .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
    	 
    	    // Group the text words as message keys
    	    .groupBy((key, value) -> value)
    	 
    	    // Count the occurrences of each word (message key).
    	    .count();
    	 
    	// Store the running counts as a changelog stream to the output topic.
    	wordCounts.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));
    }
    */
 
}