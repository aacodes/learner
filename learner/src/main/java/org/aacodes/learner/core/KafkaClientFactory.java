package org.aacodes.learner.core;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaClientFactory {

    @Value("${bootstrap.servers}")
    String serverConn;

    /**
     * The producer is thread-safe. The default properties are taken from the documentation
     * https://kafka.apache.org/21/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
     *
     * The producer's close method will be called by spring when this bean is destroyed.
     */
    @Bean
    public Producer<String, String> getProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", this.serverConn);
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30000);
        props.put("request.timeout.ms", 3000);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

}
