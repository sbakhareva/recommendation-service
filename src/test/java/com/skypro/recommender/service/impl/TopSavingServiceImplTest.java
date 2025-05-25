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
class TopSavingServiceImplTest {

    @Mock
    private RecommendationsRepository recommendationsRepository;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @InjectMocks
    private TopSavingServiceImpl topSavingService;

    @Test
    void getRecommendation_WhenRulesFollowed() {
        UUID userId = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        when(recommendationInfoRepository.getRecommendationName(recommendationId)).thenReturn("рекомендация");
        when(recommendationInfoRepository.getRecommendationDescription(recommendationId)).thenReturn("описание");

        when(recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId)).thenReturn(true);
        when(recommendationsRepository.getTotalDebitDeposit(userId)).thenReturn(51000);
        when(recommendationsRepository.getTotalSavingDeposit(userId)).thenReturn(51000);
        when(recommendationsRepository.getTotalDebitDeposit(userId)).thenReturn(10307);
        when(recommendationsRepository.getTotalDebitWithdraw(userId)).thenReturn(10200);

        String response = String.valueOf(topSavingService.getRecommendation(userId));
        assertThat(response).contains("рекомендация");
        assertThat(response).contains("описание");
        assertThat(response).contains(recommendationId.toString());
    }

    @Test
    void getRecommendation_WhenRulesNotFollowed() {
        UUID userId = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        when(recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId)).thenReturn(false);

        Optional<RecommendationDTO> response = topSavingService.getRecommendation(userId);
        assertThat(response).isEqualTo(Optional.empty());
    }
}