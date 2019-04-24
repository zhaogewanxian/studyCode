package com.wanxian.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 生产者示例
 */
public class ProducerMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("wanxian2", 0, "message", "晚上好");
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
        System.out.println(future.get());
    }
}
