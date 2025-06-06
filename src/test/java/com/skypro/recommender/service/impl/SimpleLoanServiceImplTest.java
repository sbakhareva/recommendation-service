package com.skypro.recommender.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class SimpleLoanServiceImplTest {
//
//    @Mock
//    private RecommendationsRepository recommendationsRepository;
//    @Mock
//    private RecommendationInfoRepository recommendationInfoRepository;
//    @InjectMocks
//    private SimpleLoanServiceImpl simpleLoanService;
//
//    @Test
//    void getRecommendation_WhenRulesFollowed() {
//        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
//        UUID recommendationId = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
//
//        when(recommendationInfoRepository.getRecommendationName(recommendationId)).thenReturn("рекомендация");
//        when(recommendationInfoRepository.getRecommendationDescription(recommendationId)).thenReturn("описание");
//
//        when(recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId)).thenReturn(false);
//        when(recommendationsRepository.getTotalDebitDeposit(userId)).thenReturn(133000);
//        when(recommendationsRepository.getTotalDebitWithdraw(userId)).thenReturn(123000);
//
//        String response = String.valueOf(simpleLoanService.getRecommendationByDynamicRules(userId));
//        assertThat(response).contains("рекомендация");
//        assertThat(response).contains("описание");
//        assertThat(response).contains(recommendationId.toString());
//    }
//
//    @Test
//    void getRecommendation_WhenRulesNotFollowed() {
//        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");
//        UUID recommendationId = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
//
//        when(recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId)).thenReturn(true);
//
//        Optional<Recommendation> response = simpleLoanService.getRecommendationByDynamicRules(userId);
//        assertThat(response).isEqualTo(Optional.empty());
//    }
}