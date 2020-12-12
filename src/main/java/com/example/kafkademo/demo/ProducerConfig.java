package com.example.kafkademo.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class ProducerConfig {

    private List<String> bootstrap_servers;
    private String acks;
    private int retries;
    private int batch_size;
    private String key_serializer;
    private String value_serializer;

    public List<String> getBootstrap_servers() {
        return bootstrap_servers;
    }

    public void setBootstrap_servers(List<String> bootstrap_servers) {
        this.bootstrap_servers = bootstrap_servers;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getBatch_size() {
        return batch_size;
    }

    public void setBatch_size(int batch_size) {
        this.batch_size = batch_size;
    }

    public String getKey_serializer() {
        return key_serializer;
    }

    public void setKey_serializer(String key_serializer) {
        this.key_serializer = key_serializer;
    }

    public String getValue_serializer() {
        return value_serializer;
    }

    public void setValue_serializer(String value_serializer) {
        this.value_serializer = value_serializer;
    }

    //    @Bean
//    public ProducerConfig ProducerConfig(){
//        Properties props = new Properties();
//
//    }



}
