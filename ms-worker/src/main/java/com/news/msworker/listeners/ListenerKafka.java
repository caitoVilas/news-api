package com.news.msworker.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.news.msworker.exceptions.ExternalApiException;
import com.news.msworker.persisence.repositories.NewsRepository;
import com.news.msworker.services.contracts.MediaStackService;
import com.news.msworker.utils.constants.Constants;
import com.news.msworker.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * ListenerKafka is a component that listens for messages from a Kafka topic.
 * It processes the received date, sends a request to the MediaStack service,
 * and saves the news data to the repository.
 *
 * @author caito
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerKafka {
    private final MediaStackService mediaStackService;
    private final NewsRepository newsRepository;

    /**
     * Listens for messages on the specified Kafka topic.
     * When a message is received, it processes the date, sends a request to the MediaStack service,
     * and saves the news data to the repository.
     *
     * @param date The date received from the Kafka message.
     */
    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.MESAGE_GROUP_NAME)
    public void listener(String date) {
        log.info(WriteLog.logInfo("Received message from Kafka: " + date));
        mediaStackService.senRequest(date)
                .flatMap(response -> {
                    try {
                      return newsRepository.saveNews(date, response.getBody());
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException("Error processing JSON: " + e.getMessage()));
                    }
                })
                .doOnNext(saved -> {
                    if (saved)
                        log.info(WriteLog.logInfo("News saved successfully for date: " + date));
                    else
                        log.warn(WriteLog.logWarning("No news saved for date: " + date));
                })
                .doOnError(error -> {
                    if (error instanceof ExternalApiException apiException){
                        log.error(WriteLog.logError("Error processing JSON: " + apiException.getStatus() + " - " +
                                apiException.getResponseBody()));
                    }else {
                        log.error(WriteLog.logError("Unexpected error: " + error.getMessage()));
                    }
                })
                .subscribe();
    }
}
