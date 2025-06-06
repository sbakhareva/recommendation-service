package com.skypro.recommender.service;

import com.pengrad.telegrambot.TelegramBot;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
