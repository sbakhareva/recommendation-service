package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleLoanServiceImplTest {

    @Mock
    private RecommendationsRepository recommendationsRepository;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @InjectMocks
    private SimpleLoanServiceImpl simpleLoanService;

    @Test
    void getRecommendation_WhenRulesFollowed() {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID recommendationId = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

        when(recommendationInfoRepository.getRecommendationName(recommendationId)).thenReturn("рекомендация");
        when(recommendationInfoRepository.getRecommendationDescription(recommendationId)).thenReturn("описание");

        when(recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId)).thenReturn(false);
        when(recommendationsRepository.getTotalDebitDeposit(userId)).thenReturn(133000);
        when(recommendationsRepository.getTotalDebitWithdraw(userId)).thenReturn(123000);

        String response = String.valueOf(simpleLoanService.getRecommendation(userId));
        assertThat(response).contains("рекомендация");
        assertThat(response).contains("описание");
        assertThat(response).contains(recommendationId.toString());
    }

    @Test
    void getRecommendation_WhenRulesNotFollowed() {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID recommendationId = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

        when(recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId)).thenReturn(true);

        Optional<RecommendationDTO> response = simpleLoanService.getRecommendation(userId);
        assertThat(response).isEqualTo(Optional.empty());
    }
}