package com.news.msnews.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing error codes and messages for the News Microservice.
 * Each enum constant corresponds to a specific error scenario.
 *
 * @author caito
 *
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {

    INVALID_PARAMETERS("MS_NEWS_001", "Invalid parameters provided"),
    INTERNAL_SERVER_ERROR("MS_NEWS_002", "Internal server error occurred");

    private final String code;
    private final String message;
}
