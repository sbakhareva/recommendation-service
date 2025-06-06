package com.skypro.recommender.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.skypro.recommender.service.MessagingService;
import com.skypro.recommender.service.StaticRecommendationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TelegramBotUpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private int lastUpdateId = 0;

    private final TelegramBot telegramBot;
    private final StaticRecommendationService recommendationService;
    private final MessagingService messagingService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      StaticRecommendationService recommendationService,
                                      MessagingService messagingService) {
        this.telegramBot = telegramBot;
        this.recommendationService = recommendationService;
        this.messagingService = messagingService;
    }

    @PostConstruct
    public void startListening() {
        Thread thread = new Thread(() -> {
            int offset = 0;
            while (true) {
                try {
                    GetUpdates getUpdatesRequest = new GetUpdates().offset(lastUpdateId + 1).limit(100).timeout(30);
                    GetUpdatesResponse response = telegramBot.execute(getUpdatesRequest);
                    List<Update> updates = response.updates();

                    for (Update update : updates) {
                        process(update);
                        lastUpdateId = update.updateId();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void process(Update update) {
        logger.info("Processing update: {}", update);
        if (update.message() != null && update.message().text() != null) {
            String text = update.message().text();
            long chatId = update.message().chat().id();

            if ("/start".equals(text)) {
                messagingService.sendWelcomeMessage(chatId);
            } else if (text.startsWith("/recommend")) {
                messagingService.sendRecommendationMessage(chatId, text);
                try {
                    messagingService.sendRecommendation(chatId, text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                messagingService.sendUnknownCommandMessage(chatId, text);
            }
        }
    }
}
