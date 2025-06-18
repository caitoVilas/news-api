package com.news.msnews.service.impl;

import com.news.msnews.persistence.repositories.NewsRepository;
import com.news.msnews.service.contracts.NewsService;
import com.news.msnews.utils.constants.Constants;
import com.news.msnews.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the NewsService interface that handles publishing news to a Kafka message broker
 * and retrieving news from a repository.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewsRepository newsRepository;

    /**
     * Publishes a news item to the Kafka message broker.
     *
     * @param date The date of the news item to be published.
     * @return A Mono that completes when the message is sent.
     */
    @Override
    public Mono<Void> publishToMessageBroker(String date) {
        log.info(WriteLog.logInfo("Publishing news to message broker for date: " + date));
        ProducerRecord<String, String> record = new ProducerRecord<>(Constants.TOPIC_NAME, null, date);
        return Mono.fromFuture(kafkaTemplate.send(record)).then();
    }

    /**
     * Retrieves news for a specific date. If the news is not found in the repository,
     * it publishes a request to the message broker.
     *
     * @param date The date for which news is requested.
     * @return A Mono containing the news data or a request to the message broker if not found.
     */
    @Override
    public Mono<Object> getNews(String date) {
        log.info(WriteLog.logInfo("Retrieving news for date: " + date));
        return newsRepository.getnews(date)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.defer(() -> publishToMessageBroker(date)));
    }
}
