package com.skypro.recommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuitableRecommendationsException extends NoSuchElementException {
    public NoSuitableRecommendationsException() {
        super("К сожалению, для Вас нет подходящих рекомендаций!");
    }
}
