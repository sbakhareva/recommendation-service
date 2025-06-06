package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.model.dto.RecommendationDTOMapper;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Invest500ServiceImplTest {

    @Mock
    private RecommendationsRepository recommendationsRepository;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @Mock
    private RecommendationDTOMapper recommendationDTOMapper;
    @InjectMocks
    private Invest500ServiceImpl invest500Service;

    @Test
    void getRecommendation_WhenRulesFollowed() {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID recommendationId = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

        when(recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId)).thenReturn(true);
        when(recommendationsRepository.checkIfUserHasTransactionTypeInvest(userId)).thenReturn(false);
        when(recommendationsRepository.getTotalSavingDeposit(userId)).thenReturn(10307);
        when(recommendationDTOMapper.apply(any()))
                .thenReturn(new RecommendationDTO(recommendationId, "рекомендация", "описание"));

        Optional<RecommendationDTO> response = invest500Service.getRecommendation(userId);
        assertThat(response.toString()).contains("рекомендация");
        assertThat(response.toString()).contains("описание");
        assertThat(response.toString()).contains(recommendationId.toString());
    }

    @Test
    void getRecommendation_WhenRulesNotFollowed() {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID recommendationId = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

        when(recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId)).thenReturn(false);

        Optional<RecommendationDTO> response = invest500Service.getRecommendation(userId);
        assertThat(response).isEqualTo(Optional.empty());
    }
}