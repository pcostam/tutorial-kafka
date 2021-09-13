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

public class ProducerJson {

    public static <CustomObject> void main(String [] args) {

        final Logger logger = LoggerFactory.getLogger(ProducerJson.class);
        //Create properties object for producer
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");



        //Create the producer
        final KafkaProducer<String, kafka.app.TestObject> producer = new KafkaProducer<>(properties, new StringSerializer(),
                new kafka.app.KafkaJsonSerializer());

        for(int i = 0; i < 5 ; ++i) {
            kafka.app.TestObject testObject = new kafka.app.TestObject();
            testObject.setCountry("Portugal");
            //Create the producer record
            ProducerRecord<String, kafka.app.TestObject> record = new ProducerRecord<>("sample-topic", "0", testObject);

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