package com.skypro.recommender.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private RecommendationsRepository recommendationsRepository;
    @Mock
    private StaticRecommendationService staticRecommendationService;
    @Mock
    private DynamicRecommendationService dynamicRecommendationService;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @InjectMocks
    private MessagingService messagingService;

    @Test
    void sendWelcomeMessageTest() {
        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            String testWelcomeMessage = "Тестовое сообщение";
            filesMock.when(() -> Files.readString(any(java.nio.file.Path.class))).thenReturn(testWelcomeMessage);

            long chatId = 1L;

            messagingService.sendWelcomeMessage(chatId);

            ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
            verify(telegramBot).execute(captor.capture());

            SendMessage sentMessage = captor.getValue();
            verify(telegramBot).execute(sentMessage);
            assertTrue(sentMessage.getParameters().containsValue(testWelcomeMessage));
            assertTrue(sentMessage.getParameters().containsValue(chatId));
        }
    }

    @Test
    void sendRecommendation_WhenThereIsSuitableRecommendations() throws IOException {
        String username = "username";
        UUID userId = UUID.randomUUID();
        when(recommendationsRepository.getUserIdByUsername(anyString())).thenReturn(userId);

        UUID recommendationId = UUID.randomUUID();
        RecommendationDTO recommendation = new RecommendationDTO(recommendationId, "название", "описание");
        when(dynamicRecommendationService.getRecommendations(any())).thenReturn(List.of(recommendation));
        when(staticRecommendationService.getRecommendations(any())).thenReturn(List.of());

        when(recommendationInfoRepository.getRecommendationName(recommendationId))
                .thenReturn("Simple Loan");

        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            String testRecommendationMessage = "Simple Loan description";
            filesMock.when(() -> Files.readString(any(java.nio.file.Path.class))).thenReturn(testRecommendationMessage);
            long chatId = 1L;

            messagingService.sendRecommendation(chatId, username);

            ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
            verify(telegramBot).execute(captor.capture());

            SendMessage sentMessage = captor.getValue();
            verify(telegramBot).execute(sentMessage);
            assertTrue(sentMessage.getParameters().containsValue(testRecommendationMessage));
            assertTrue(sentMessage.getParameters().containsValue(chatId));
        }
    }

    @Test
    void getRecommendation_WhenThereIsNoSuitableRecommendations() throws IOException {
        String username = "username";
        UUID userId = UUID.randomUUID();
        when(recommendationsRepository.getUserIdByUsername(anyString())).thenReturn(userId);

        long chatId = 1L;

        UUID recommendationId = UUID.randomUUID();
        when(dynamicRecommendationService.getRecommendations(any())).thenReturn(Collections.emptyList());
        when(staticRecommendationService.getRecommendations(any())).thenReturn(Collections.emptyList());

        messagingService.sendRecommendation(chatId, username);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(captor.capture());

        SendMessage sentMessage = captor.getValue();
        verify(telegramBot).execute(sentMessage);
        assertTrue(sentMessage.getParameters().containsValue("К сожалению, для Вас не найдено подходящих продуктов!"));
    }
}
