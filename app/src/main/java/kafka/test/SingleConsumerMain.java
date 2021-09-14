package kafka.app;

public class SingleConsumerMain {
    public static void main(String[] args) {
        //should be higher than partitions
        int numberOfThread = 3;
        String brokers = "localhost:9092";
        String groupId = "java-group-consumer";
        String topic = "sample-topic";

        // Start group of Notification Consumer Thread
        kafka.app.NotificationConsumer consumers = new kafka.app.NotificationConsumer(brokers, groupId, topic);

        consumers.execute(numberOfThread);

     
        consumers.shutdown();
    }


}
