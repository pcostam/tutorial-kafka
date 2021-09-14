package kafka.app;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerJson {
    public static void main(String [] args) {
        final Logger logger = LoggerFactory.getLogger(kafka.app.Consumer.class);

        final String bootstrapServers = "localhost:9092";
        final String consumerGroupID = "java-group-consumer";
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafka.app.KafkaJsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //create consumer
        final KafkaConsumer<String, kafka.app.TestObject> consumer = new KafkaConsumer(properties, new StringDeserializer(),
                new kafka.app.KafkaJsonDeserializer(kafka.app.TestObject.class));

        //subscribe to topics

        consumer.subscribe(Arrays.asList("sample-topic"));

        //poll and consume records
        // single-threaded implementation
        while(true) {
            ConsumerRecords<String, kafka.app.TestObject> records = consumer.poll(Duration.ofMillis(1000));

            for(ConsumerRecord record: records) {
                logger.info( "Received new records: " + " key: " + record.key() + " value: " + record.value() + "\n");
            }
        }


    }
}
