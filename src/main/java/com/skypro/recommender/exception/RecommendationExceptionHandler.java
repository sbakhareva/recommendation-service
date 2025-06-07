package com.skypro.recommender.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RecommendationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationExceptionHandler.class);

    @ExceptionHandler(NoSuitableRecommendationsException.class)
    public ResponseEntity<String> handleNoMatchingResultsException(NoSuitableRecommendationsException e) {
        logger.warn(e.getMessage(), e);
        ResponseStatus status = e.getClass().getAnnotation(ResponseStatus.class);
        return new ResponseEntity<>(e.getMessage(), status.code());
    }

    @ExceptionHandler(IdIsNotFoundException.class)
    public ResponseEntity<String> handleIdIsNotFoundException(IdIsNotFoundException e) {
        logger.warn(e.getMessage(), e);
        ResponseStatus status = e.getClass().getAnnotation(ResponseStatus.class);
        return new ResponseEntity<>(e.getMessage(), status.code());
    }
}
