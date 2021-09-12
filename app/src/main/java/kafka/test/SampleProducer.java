package kafka.app;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
/* references: https://www.youtube.com/watch?v=bwXWIx5dZjw*/
public class SampleProducer {
	public SampleProducer() {
		Properties properties = new Properties();
		//check port
		properties.put("bootstrap.servers", "localhost:9092");

		//serialize object 
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		/*Crate new producer object - Producer record*/
		ProducerRecord producerRecord = new ProducerRecord("channel", "name", "selftuts");

		KafkaProducer kafkaProducer = new KafkaProducer(properties);
		kafkaProducer.send(producerRecord);
		kafkaProducer.close();
	}
}