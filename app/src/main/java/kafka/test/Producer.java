//References
/*
* https://www.youtube.com/watch?v=Aizl0PWzhnw
*/

package kafka.app;
import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer; 
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Producer {

	public static void main(String [] args) {
		
		final Logger logger = LoggerFactory.getLogger(Producer.class);
		//Create properties object for producer
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());



		//Create the producer
		final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		for(int i = 0; i < 5 ; ++i) {
			//Create the producer record
		ProducerRecord<String, String> record = new ProducerRecord<>("sample-topic",  "key" + i, "val" + i);

		//Send data - assynchronous
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception e) {
				if( e == null) {
					logger.info("received record metadata \n " + recordMetadata.topic());

				} else {
					logger.error("Error: Occured", e);

				}
			}
		});
		}
		
		//flush and close producer
		producer.flush();
		producer.close();

	}
}