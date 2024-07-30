package org.example;

import org.example.kafka.KafkaService;

public class GreetingConsumer {

    public static void main(String[] args) {
            var service = new KafkaService<String>(
                    "GREETING",
                    (record) -> System.out.println("Topic: " + record.topic()
                            + " - Key: " + record.key()
                            + " - Value: " + record.value()),
                    GreetingConsumer.class.getName());
            service.run();
    }

}
