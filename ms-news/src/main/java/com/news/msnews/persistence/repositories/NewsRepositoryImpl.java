package com.news.msnews.persistence.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * This class implements the NewsRepository interface, providing a concrete implementation
 * for retrieving news from a Redis data store based on a given date.
 * It uses Spring Data Redis's ReactiveRedisOperations for asynchronous operations.
 *
 * @author caito
 *
 */
@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository{
    private final ReactiveRedisOperations<String, Object> redisOperations;

    /**
     * Retrieves news from Redis based on the provided date.
     *
     * @param date The date for which news is to be retrieved.
     * @return A Mono containing the news object if found, or empty if not found.
     */
    @Override
    public Mono<Object> getnews(String date) {
        return redisOperations.opsForValue().get(date);
    }
}
