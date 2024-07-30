package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.kafka.KafkaService;
import org.example.model.Greeting;

public class GreetingConsumer {

    public static void main(String[] args) {
            var service = new KafkaService<>(
                    "GREETING",
                    (ConsumerRecord<String, Greeting> record) -> System.out.println("Topic: " + record.topic()
                            + " - Key: " + record.key()
                            + " - Value: " + record.value()),
                    GreetingConsumer.class.getName());
            service.run();
    }

}
