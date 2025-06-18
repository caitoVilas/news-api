package com.news.msworker.services.impl;

import com.news.msworker.exceptions.ExternalApiException;
import com.news.msworker.services.contracts.MediaStackService;
import com.news.msworker.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * MediaStackServiceImpl is an implementation of the MediaStackService interface.
 * It handles requests to the MediaStack API, including error handling and logging.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MediaStackServiceImpl implements MediaStackService {
    private final WebClient webClient;

    @Value("${mediastack.api-key}")
    private String apiKey;
    @Value("${mediastack.countries}")
    private String countries;
    @Value("${mediastack.limit}")
    private String limit;

    /**
     * Sends a request to the MediaStack API with the specified date.
     *
     * @param date The date for which to retrieve news data.
     * @return A Mono containing the ResponseEntity with the response from MediaStack.
     */
    @Override
    public Mono<ResponseEntity<String>> senRequest(String date) {
    log.info(WriteLog.logInfo("Sending request to MediaStack with date: " + date));
        return webClient.get()
                .uri(ub -> ub
                        .queryParam("access_key", apiKey)
                        .queryParam("countries", countries)
                        .queryParam("limit", limit)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    log.error(WriteLog.logError("Error response from MediaStack: " + body));
                                    return Mono.error(new ExternalApiException((HttpStatus) response.statusCode(),
                                            body));
                                }))
                .toEntity(String.class);
    }
}
