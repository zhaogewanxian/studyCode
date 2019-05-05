package com.wanxian.springcloud.raw.api;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * kafka原生 api
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        //多个参数，进行定义，再传入
        String topic = "test";
        Integer partition = 0;
        Long timestamp = System.currentTimeMillis();
        String key = "name";
        String value = "晚弦";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, partition, timestamp, key, value);
        //发送消息，返回future
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
        //强制执行
        future.get();
    }
}
