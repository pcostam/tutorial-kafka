package kafka.app;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;

public class ConsumerThread implements Runnable {

        private ConsumerRecord consumerRecord;

        public ConsumerThread(ConsumerRecord consumerRecord) {
            this.consumerRecord = consumerRecord;
        }

        public void run() {
            System.out.println("Process: " + consumerRecord.value() + ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

}
