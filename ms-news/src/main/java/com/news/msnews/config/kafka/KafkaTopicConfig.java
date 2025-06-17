package com.news.msnews.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka Topic Configuration
 * This configuration sets up the Kafka topic for news messages.
 *
 * @author caito
 *
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a new Kafka topic named "news-topic".
     *
     * @return a NewTopic object representing the news topic
     */
    @Bean
    NewTopic newsTopic() {
        String TOPIC_NAME = "news-topic";
        return TopicBuilder
                .name(TOPIC_NAME)
                .build();
    }
}
