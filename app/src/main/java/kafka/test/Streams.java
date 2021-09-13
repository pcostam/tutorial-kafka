package kafka.app;

import  java.lang.Runtime;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KTable;
import java.util.Properties;
import org.apache.kafka.streams.kstream.Produced;
/*
References:
https://www.baeldung.com/java-kafka-streams
*/
public class Streams {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	public static void main (String[] args) {
		//define configuration properties
		Properties properties = new Properties();
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-first-streams-application");
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		//properties.put(StreamsConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());
		properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		//open a stream to a source topic
		StreamsBuilder streamsBuilder = new StreamsBuilder();
		KStream<String, String> kstream = streamsBuilder.stream("sample-topic");
		//map values to uppercase
		
	
  		KStream<String, String> upperStream = kstream.mapValues(String::toUpperCase);
    	upperStream.to(Serdes.String(), Serdes.String(), "out-topic");

		//process the stream
		//kstream.foreach((k,v)-> System.out.println("key=" + k + "value =" + v));
		//create a topology
		Topology topology = streamsBuilder.build();

		KafkaStreams streams = new KafkaStreams(topology, properties);
		streams.start();


		// Make sure everything terminates cleanly when process is killed
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
           	logger.info("shutting down");
           	streams.close();
        }));

	}
}