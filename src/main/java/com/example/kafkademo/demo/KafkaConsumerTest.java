package com.example.kafkademo.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.PropertyConfigurator;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerTest implements Runnable{

    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;
    private static final String GROUP_ID = "groupD";

    public KafkaConsumerTest(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.10.10.28:9092");
        props.put("group.id", GROUP_ID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("max.poll.records",10);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topic;
        this.consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            a:
            for (; ; ) {
                msgList = consumer.poll(1000);
                if (null != msgList && msgList.count() > 0) {
                    for (ConsumerRecord<String, String> record : msgList) {
                        if (messageNo % 10 == 0){
                            System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = "
                                    + record.value()+" offset==="+record.offset());
                        }
                        if (messageNo % 100 == 0) {
                            break a;
                        }
                        messageNo++;
                    }
                }else {
                    System.out.println("消费失败");
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        KafkaConsumerTest test = new KafkaConsumerTest("KAFKA_TEST1");
        Thread thread = new Thread(test);
        thread.start();
    }
}
