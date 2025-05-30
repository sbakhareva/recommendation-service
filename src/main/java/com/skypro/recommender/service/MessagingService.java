package com.skypro.recommender.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MessagingService {

    private final TelegramBot telegramBot;
    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationService recommendationService;
    private final RecommendationInfoRepository recommendationInfoRepository;


    public MessagingService(TelegramBot telegramBot,
                            RecommendationsRepository recommendationsRepository,
                            RecommendationService recommendationService,
                            RecommendationInfoRepository recommendationInfoRepository) {
        this.telegramBot = telegramBot;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationService = recommendationService;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    public void sendWelcomeMessage(long chatId) {
        String filename = "welcome-message.txt";
        Path path = Paths.get("src/main/resources/messages", filename);

        try {
            String content = Files.readString(path);
            SendMessage response = new SendMessage(chatId, content);
            telegramBot.execute(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractUsername(String text) {
        String prefix = "/recommend";
        if (text.startsWith(prefix)) {
            String[] parts = text.split("\\s+");
            if (parts.length >= 2) {
                return parts[1];
            }
        }
        return "Имя пользователя не найдено!";
    }

    public void sendRecommendationMessage(long chatId, String message) {
        try {
            String firstName = recommendationsRepository.getFirstNameByUsername(extractUsername(message));
            String lastName = recommendationsRepository.getLastNameByUsername(extractUsername(message));
            String text = "Здравствуйте, " + firstName + " " + lastName + "!" + "\n\nРекомендации для Вас:\n";
            SendMessage response = new SendMessage(chatId, text);
            telegramBot.execute(response);
        } catch (EmptyResultDataAccessException e) {
            SendMessage exception = new SendMessage(chatId, "Пользователь не найден");
        }

    }

    public void sendRecommendation(long chatId, String message) throws IOException {
        try {
            UUID userId = recommendationsRepository.getUserIdByUsername(extractUsername(message));
            List<RecommendationDTO> recommendations = recommendationService.getRecommendation(userId);
            for (RecommendationDTO recommendation : recommendations) {
                String name = recommendationInfoRepository.getRecommendationName(recommendation.getId());
                switch (name) {
                    case "Simple Loan" -> {
                        String text = Files.readString(Path.of("src/main/resources/messages/SimpleLoanMessage.txt"));
                        SendMessage response = new SendMessage(chatId, text);
                        telegramBot.execute(response);
                    }
                    case "Invest 500" -> {
                        String text = Files.readString(Path.of("src/main/resources/messages/Invest500Message.txt"));
                        SendMessage response = new SendMessage(chatId, text);
                        telegramBot.execute(response);
                    }
                    case "Top Saving" -> {
                        String text = Files.readString(Path.of("src/main/resources/messages/TopSavingMessage.txt"));
                        SendMessage response = new SendMessage(chatId, text);
                        telegramBot.execute(response);
                    }
                }
            }
        } catch (EmptyResultDataAccessException e) {
            SendMessage exception = new SendMessage(chatId, "Пользователь не найден");
        }
    }
}
