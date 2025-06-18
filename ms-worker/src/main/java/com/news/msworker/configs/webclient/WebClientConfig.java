package com.news.msworker.configs.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for setting up a WebClient instance.
 * This client is used to make HTTP requests to the MediaStack API.
 *
 * @author caito
 *
 */
@Configuration
public class WebClientConfig {

    @Value("${mediastack.uri}")
    private String apiUri;

    /**
     * Creates a WebClient bean configured with the base URL from application properties.
     * The client is set to accept JSON responses by default.
     *
     * @return a configured WebClient instance
     */
    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUri)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
