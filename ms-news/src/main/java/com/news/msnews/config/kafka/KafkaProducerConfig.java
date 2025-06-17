package com.news.msnews.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Configuration
 * This configuration sets up the Kafka producer with necessary properties.
 *
 * @author caito
 *
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Configures the producer properties.
     *
     * @return a map of producer properties
     */
    @Bean
    Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    /**
     * Creates a ProducerFactory bean.
     *
     * @return a ProducerFactory for String keys and String values
     */
    @Bean
    ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Creates a KafkaTemplate bean.
     *
     * @return a KafkaTemplate for sending messages to Kafka
     */
    @Bean
    KafkaTemplate<String, String> kafkaTemplate() {
        return  new KafkaTemplate<>(producerFactory());
    }
}
