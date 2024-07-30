package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;
    private final String groupId;

    public KafkaService(String topic, ConsumerFunction<T> parse, String groupId) {
        this.groupId = groupId;
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties());
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(1000));
            if(!records.isEmpty()) {
                System.out.println(records.count() + " records found.");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    @Override
    public void close() {
        consumer.close();
    }

    private Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return properties;
    }
}
