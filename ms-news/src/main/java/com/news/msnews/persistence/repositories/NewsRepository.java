package com.news.msnews.persistence.repositories;

import reactor.core.publisher.Mono;

/**
 * This interface defines a contract for a repository that retrieves news based on a given date.
 * It uses Project Reactor's Mono to handle asynchronous operations.
 *
 * @author caito
 *
 */
public interface NewsRepository {
    Mono<Object> getnews(String date);
}
