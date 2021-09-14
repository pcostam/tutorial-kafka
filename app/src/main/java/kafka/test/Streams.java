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
http://codingjunkie.net/kafka-streams-yelling-app/
https://www.youtube.com/watch?v=SQuh7CI1DV8
bin\windows\kafka-console-producer.bat --topic input-topic --bootstrap-server localhost:9092

bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic out-topic --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print-key=true --property print-value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
*/
public class Streams {
	private static final Logger logger = LoggerFactory.getLogger(Streams.class);

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
		KStream<String, String> kstream = streamsBuilder.stream("input-topic");
		//map values to uppercase
		
	
  		KStream<String, String> upperStream = kstream.mapValues(v -> v.toUpperCase());
    	upperStream.through("out-topic", Produced.with(Serdes.String(), Serdes.String()));

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