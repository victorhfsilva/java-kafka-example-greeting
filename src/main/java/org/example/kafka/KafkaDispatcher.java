package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.serializer.GsonSerializer;

import java.io.Closeable;
import java.util.Properties;

public class KafkaDispatcher<T> implements Closeable {

    public final KafkaProducer<String, T> producer;

    public KafkaDispatcher() {
        producer = new KafkaProducer<String, T>(properties());
    }

    public void send(String topic, String key, T value) {
        var record = new ProducerRecord<String, T>(topic, key, value);
        producer.send(record, (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Topic: " + data.topic() +
                    " - Partition: " + data.partition() +
                    " - Offset: " + data.offset() +
                    " - Timestamp: " + data.timestamp());
        });
    }

    @Override
    public void close() {
        producer.close();
    }

    private static Properties properties(){
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }
}
