package com.news.msworker.persisence.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Implementation of the NewsRepository interface for saving news data in Redis.
 * Uses ReactiveRedisOperations to perform non-blocking operations.
 *
 * @author caito
 *
 */
@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements  NewsRepository {
    private final ReactiveRedisOperations<String, Object> redisOperations;

    /**
     * Saves a news object associated with a specific date in Redis.
     * The news object is serialized to JSON format and stored with a TTL of 1 hour.
     *
     * @param date the date associated with the news object
     * @param newsObject the news object to be saved
     * @return a Mono indicating the success or failure of the operation
     * @throws JsonProcessingException if there is an error during serialization
     */
    @Override
    public Mono<Boolean> saveNews(String date, Object newsObject) throws JsonProcessingException {
        Duration ttl = Duration.ofHours(1);
        ObjectMapper mapper = new ObjectMapper();
        return redisOperations.opsForValue()
                .set(date, mapper.readTree(newsObject.toString()), ttl);
    }
}
