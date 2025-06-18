package com.news.msworker.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class for handling external API errors.
 * This exception is thrown when an external API call fails.
 * It includes the HTTP status and the response body for debugging purposes.
 *
 * @author caito
 *
 */
@Getter@Setter
public class ExternalApiException extends RuntimeException{
    private final HttpStatus status;
    private final String responseBody;

    public ExternalApiException(HttpStatus status, String responseBody) {
        super("External API call failed with status: " + status + " and response: " + responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }
}
