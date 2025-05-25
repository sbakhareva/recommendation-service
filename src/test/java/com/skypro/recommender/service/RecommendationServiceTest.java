package com.skypro.recommender.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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


    }
}