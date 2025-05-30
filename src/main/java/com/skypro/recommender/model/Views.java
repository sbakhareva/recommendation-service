package com.skypro.recommender.model;

public class Views {

    public static class Request { }  // Для входящих запросов (клиент → сервер)

    public static class Response extends Request { }  // Для ответов (сервер → клиент)
}
