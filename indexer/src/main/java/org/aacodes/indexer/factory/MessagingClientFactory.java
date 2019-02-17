package org.aacodes.indexer.factory;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class MessagingClientFactory {

    @Autowired
    Environment env;

    /**
     * The consumer is not thread safe, so each time getConsumer is called a new instance is provided by the IoC
     * https://kafka.apache.org/21/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Consumer<String, String> getConsumer() {
        System.out.println("Connecting to kafka server via: " + this.env.getProperty("bootstrap.servers"));
        Properties props = new Properties();
        props.put("bootstrap.servers", this.env.getProperty("bootstrap.servers"));
        props.put("group.id", "vehicles-group");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(props);
    }

}

