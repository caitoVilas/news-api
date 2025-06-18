package com.news.msnews.api.controllers;

import com.news.msnews.api.models.responses.DataResponse;
import com.news.msnews.service.contracts.NewsService;
import com.news.msnews.utils.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller for handling news-related requests.
 * Provides an endpoint to retrieve news based on a specified date.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public Mono<ResponseEntity<DataResponse<Object>>> getNews(@NotBlank(message = Constants.DATE_NOT_BLANK_MESSAGE)
                                                              @Pattern(regexp = Constants.DATE_FORMAT, message =
                                                                      Constants.DATE_PATTERN_MESSAGE)
                                                              @RequestParam String date) {
        return newsService.getNews(date)
                .flatMap(data -> Mono.just(ResponseEntity.ok()
                        .body(DataResponse.builder()
                                .status(Boolean.TRUE)
                                .message(Constants.DATA_FOUND_MESSAGE)
                                .data(data)
                                .build())))
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(DataResponse.builder()
                                .status(Boolean.FALSE)
                                .message(Constants.DATA_NOT_FOUND_MESSAGE)
                                .build()))));
    }
}
