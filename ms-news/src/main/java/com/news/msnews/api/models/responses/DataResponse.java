package com.news.msnews.api.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a generic data response with a message, status, and optional data.
 *
 * @param <T> the type of the data contained in the response
 *
 * @author caito
 *
 */
@AllArgsConstructor@Data@Builder
public class DataResponse<T> implements Serializable {
    private String message;
    private boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
