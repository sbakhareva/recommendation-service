package com.skypro.recommender.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

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

//    @BeforeEach
//    void setUp() throws SQLException {
//        when(dataSource.getConnection()).thenReturn(connection);
//
//        recommendationService = new RecommendationService(
//                Arrays.asList(invest500Service, topSavingService, simpleLoanService),
//                dataSource
//        );
//    }
//
//    @Test
//    void getRecommendations_WhenSomeRulesFollowed() throws SQLException {
//        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
//
//        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
//
//        Recommendation recommendation = new Recommendation("рекомендация", recommendationId, "описание");
//
//        when(invest500Service.getRecommendation(userId)).thenReturn(Optional.of(recommendation));
//        when(topSavingService.getRecommendation(userId)).thenReturn(Optional.empty());
//        when(simpleLoanService.getRecommendation(userId)).thenReturn(Optional.empty());
//
//        List<Recommendation> response = recommendationService.getRecommendations(userId);
//        assertNotNull(response);
//        assertTrue(response.contains(recommendation));
//        assertEquals(1,response.size());
//    }
}