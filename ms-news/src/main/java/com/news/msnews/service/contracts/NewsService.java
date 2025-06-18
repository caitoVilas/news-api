package com.news.msnews.service.contracts;

import reactor.core.publisher.Mono;

/**
 * Interface for News Service that defines methods for publishing news to a message broker
 * and retrieving news based on a specific date.
 *
 * @author caito
 *
 */
public interface NewsService {
    Mono<Void> publishToMessageBroker(String date);
    Mono<Object> getNews(String date);
}
