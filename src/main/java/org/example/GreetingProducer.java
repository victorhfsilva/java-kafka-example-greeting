package org.example;

import org.example.kafka.KafkaDispatcher;
import org.example.model.Greeting;

import java.util.concurrent.ExecutionException;

public class GreetingProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var dispatcher = new KafkaDispatcher<Greeting>();
        dispatcher.send("GREETING", "hello-world", new Greeting("Hello", "World"));
        dispatcher.close();
    }

}
