package kafka.app;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer; 
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Consumer {

	public static void main(String [] args) {
			final Logger logger = LoggerFactory.getLogger(Consumer.class);

	final String bootstrapServers = "localhost:9092";
	final String consumerGroupID = "java-group-consumer";
			Properties properties = new Properties();
	
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupID);
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

			//create consumer
			final KafkaConsumer <String, String> consumer = new KafkaConsumer<String, String> (properties);

			//subscribe to topics

			consumer.subscribe(Arrays.asList("java-topic"));

			//poll and consume records
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

				for(ConsumerRecord record: records) {
					logger.info( "Received new records: \n" + record.key());
				}
			}


	}
	
}