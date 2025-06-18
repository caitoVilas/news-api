package com.news.msnews.api.models.responses;

import com.news.msnews.utils.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an error response with a code, type, message, details, and timestamp.
 *
 * @author caito
 *
 */
@AllArgsConstructor@Data@Builder
public class ErrorResponse implements Serializable {
    private String code;
    private ErrorType type;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;
}
