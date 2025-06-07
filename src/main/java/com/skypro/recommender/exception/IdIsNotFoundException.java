package com.skypro.recommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdIsNotFoundException extends RuntimeException{

    public IdIsNotFoundException() {
        super("Продукт/пользователь с таким идентификатором не найден или передано неверное значение");
    }
}
