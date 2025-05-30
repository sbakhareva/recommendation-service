package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private List<RecommendationRuleSet> rules;
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private RecommendationRuleSet invest500Service;
    @Mock
    private RecommendationRuleSet topSavingService;
    @Mock
    private RecommendationRuleSet simpleLoanService;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);

        recommendationService = new RecommendationService(
                Arrays.asList(invest500Service, topSavingService, simpleLoanService),
                dataSource
        );
    }

    @Test
    void getRecommendations_WhenSomeRulesFollowed() throws SQLException {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");

        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        ProductDTO recommendation = new ProductDTO("рекомендация", recommendationId, "описание");

        when(invest500Service.getRecommendation(userId)).thenReturn(Optional.of(recommendation));
        when(topSavingService.getRecommendation(userId)).thenReturn(Optional.empty());
        when(simpleLoanService.getRecommendation(userId)).thenReturn(Optional.empty());

        List<ProductDTO> response = recommendationService.getRecommendation(userId);
        assertNotNull(response);
        assertTrue(response.contains(recommendation));
        assertEquals(1,response.size());
    }
}