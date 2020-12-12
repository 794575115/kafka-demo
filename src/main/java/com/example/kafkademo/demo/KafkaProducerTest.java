package com.example.kafkademo.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.PropertyConfigurator;

import javax.annotation.Resource;
import java.util.Properties;

public class KafkaProducerTest implements Runnable {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public KafkaProducerTest(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.10.10.28:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        this.producer = new KafkaProducer<String, String>(props);
        this.topic = topic;
    }

    @Override
    public void run() {
        int messageNo = 1;
        String messageStr;
        try {
            for (; messageNo <= 100; messageNo++) {
                messageStr = "你好，这是第" + messageNo + "条数据";
                producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
                if (messageNo % 10 == 0) {
                    System.out.println("发送的信息:" + messageStr);
                }
                if (messageNo % 100 == 0) {
                    System.out.println("成功发送了" + messageNo + "条");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String[] args) {
        KafkaProducerTest kafka_test = new KafkaProducerTest("KAFKA_TEST1");
        Thread thread = new Thread(kafka_test);
        thread.start();
    }
}
