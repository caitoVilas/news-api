package com.news.msworker.services.contracts;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * MediaStackService interface for handling media stack requests.
 * It defines a method to send a request with a date parameter.
 *
 * @author caito
 *
 */
public interface MediaStackService {
    Mono<ResponseEntity<String>> senRequest(String date);
}
