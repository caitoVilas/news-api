package com.news.msworker.persisence.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

/**
 * Interface for a repository that handles news data.
 * Provides methods to save news objects associated with a specific date.
 *
 * @author caito
 *
 */
public interface NewsRepository {
    Mono<Boolean> saveNews(String date, Object newsObject) throws JsonProcessingException;
}
