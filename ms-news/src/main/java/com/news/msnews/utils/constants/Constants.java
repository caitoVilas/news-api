package com.news.msnews.utils.constants;

import lombok.experimental.UtilityClass;

/**
 * Constants class for the News Microservice.
 * Contains constants used throughout the application.
 *
 * @author caito
 *
 */
@UtilityClass
public class Constants {

    public static final String TOPIC_NAME = "news-topic";
    public static final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2}"; // YYYY-MM-DD format
    public static final String DATE_NOT_BLANK_MESSAGE = "Date must not be blank";
    public static final String DATE_PATTERN_MESSAGE = "Date must match the pattern YYYY-MM-DD";
    public static final String DATA_FOUND_MESSAGE = "Data found ";
    public static final String DATA_NOT_FOUND_MESSAGE = "Data not found, sending request to broker";
}