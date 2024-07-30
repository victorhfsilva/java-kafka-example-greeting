package org.example;

import org.example.kafka.KafkaDispatcher;
import org.example.model.Greeting;

public class GreetingProducer {

    public static void main(String[] args) {
        var dispatcher = new KafkaDispatcher<Greeting>();
        dispatcher.send("GREETING", "hello-world", new Greeting("Hello", "World"));
        dispatcher.close();
    }

}
