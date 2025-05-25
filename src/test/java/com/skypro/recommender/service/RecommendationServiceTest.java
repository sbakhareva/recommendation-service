package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.service.impl.Invest500ServiceImpl;
import com.skypro.recommender.service.impl.SimpleLoanServiceImpl;
import com.skypro.recommender.service.impl.TopSavingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    private RecommendationRuleSet ruleMock1;
    @Mock
    private RecommendationRuleSet ruleMock2;
    @Mock
    private RecommendationRuleSet ruleMock3;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);

        recommendationService = new RecommendationService(
                Arrays.asList(ruleMock1, ruleMock2, ruleMock3),
                dataSource
        );
    }

    @Test
    void getRecommendations_WhenRulesFollowed() throws SQLException {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");

        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        RecommendationDTO recommendation = new RecommendationDTO("рекомендация", recommendationId, "описание");

        when(ruleMock1.getRecommendation(userId)).thenReturn(Optional.of(recommendation));
        when(ruleMock2.getRecommendation(userId)).thenReturn(Optional.empty());
        when(ruleMock3.getRecommendation(userId)).thenReturn(Optional.empty());

        List<RecommendationDTO> response = recommendationService.getRecommendation(userId);
        assertNotNull(response);
        assertTrue(response.contains(recommendation));
        assertEquals(1,response.size());
    }
}