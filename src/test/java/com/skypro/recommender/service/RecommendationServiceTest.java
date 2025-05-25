package com.skypro.recommender.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private List<RecommendationRuleSet> rules;
    @Mock
    private DataSource dataSource;
    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void getRecommendations_WhenRulesFollowed() {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        String name = "рекомендация";
        String description = "описание";

        //
        //
        //

       String response = String.valueOf(recommendationService.getRecommendation(userId));
        assertThat(response).contains(userId.toString());
        assertThat(response).contains("рекомендация");
        assertThat(response).contains("описание");
        assertThat(response).contains(recommendationId.toString());
    }
}