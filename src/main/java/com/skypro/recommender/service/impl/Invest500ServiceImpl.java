package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500ServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public Invest500ServiceImpl(RecommendationsRepository recommendationsRepository,
                                RecommendationInfoRepository recommendationInfoRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    @Override
    public Optional<Recommendation> getRecommendation(UUID userId) {

        UUID id = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

        if (recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId) &&
                !recommendationsRepository.checkIfUserHasTransactionTypeInvest(userId) &&
                recommendationsRepository.getTotalSavingDeposit(userId) > 1000) {
            return Optional.of(recommendationInfoRepository.getRecommendation(id));
        }
        return Optional.empty();
    }
}
