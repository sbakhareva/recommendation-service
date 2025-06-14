package com.skypro.recommender.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.DeleteMyCommands;
import com.pengrad.telegrambot.request.DeleteWebhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        DeleteWebhook deleteWebhook = new DeleteWebhook();
        bot.execute(deleteWebhook);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
