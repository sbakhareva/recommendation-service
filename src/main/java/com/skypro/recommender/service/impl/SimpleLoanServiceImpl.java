package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleLoanServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public SimpleLoanServiceImpl(RecommendationsRepository recommendationsRepository,
                                 RecommendationInfoRepository recommendationInfoRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {

        UUID id = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f" );

        if (!recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId) &&
                (recommendationsRepository.getTotalDebitDeposit(userId) > recommendationsRepository.getTotalDebitWithdraw(userId)) &&
                recommendationsRepository.getTotalDebitWithdraw(userId) > 100000) {
            return Optional.of(
                    new RecommendationDTO(
                            recommendationInfoRepository.getRecommendationName(id),
                            id,
                            recommendationInfoRepository.getRecommendationDescription(id)));
        }
        return Optional.empty();
    }
}
