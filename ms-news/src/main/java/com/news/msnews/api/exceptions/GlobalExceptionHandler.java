package com.news.msnews.api.exceptions;

import com.news.msnews.api.models.responses.ErrorResponse;
import com.news.msnews.utils.enums.ErrorCatalog;
import com.news.msnews.utils.enums.ErrorType;
import com.news.msnews.utils.logs.WriteLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Global exception handler for the application.
 * Handles validation errors and general exceptions, returning appropriate error responses.
 *
 * @author caito
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles validation errors thrown by handler methods.
     *
     * @param ex the exception containing validation errors
     * @return an ErrorResponse with details of the validation error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponse validaionHandler(HandlerMethodValidationException ex) {
        log.error(WriteLog.logError("Validation error: " + ex.getMessage()));
        return ErrorResponse.builder()
                .code(ErrorCatalog.INVALID_PARAMETERS.getCode())
                .type(ErrorType.FUNCTIONAL)
                .message(ErrorCatalog.INVALID_PARAMETERS.getMessage())
                .details(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Handles validation errors from method arguments.
     *
     * @param ex the exception containing validation errors
     * @return an ErrorResponse with details of the validation error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse argumentsHandler(MethodArgumentNotValidException ex) {
        log.error(WriteLog.logError("Validation error: " + ex.getMessage()));
        BindingResult results = ex.getBindingResult();
        return ErrorResponse.builder()
                .code(ErrorCatalog.INVALID_PARAMETERS.getCode())
                .type(ErrorType.FUNCTIONAL)
                .message(ErrorCatalog.INVALID_PARAMETERS.getMessage())
                .details(results.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Handles general exceptions that are not caught by other handlers.
     *
     * @param ex the exception that occurred
     * @return an ErrorResponse with details of the error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(Exception ex) {
        log.error(WriteLog.logError("Error: " + ex.getMessage()));
        return ErrorResponse.builder()
                .code(ErrorCatalog.INTERNAL_SERVER_ERROR.getCode())
                .type(ErrorType.SYSTEM)
                .message(ErrorCatalog.INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
