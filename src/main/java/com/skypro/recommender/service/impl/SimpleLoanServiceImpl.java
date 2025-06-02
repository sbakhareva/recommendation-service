package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.model.dto.RecommendationDTOMapper;
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
    private final RecommendationDTOMapper recommendationDTOMapper;

    public SimpleLoanServiceImpl(RecommendationsRepository recommendationsRepository,
                                 RecommendationInfoRepository recommendationInfoRepository,
                                 RecommendationDTOMapper recommendationDTOMapper) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
        this.recommendationDTOMapper = recommendationDTOMapper;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {

        UUID id = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

        if (!recommendationsRepository.checkIfUserHasTransactionTypeCredit(userId) &&
                (recommendationsRepository.getTotalDebitDeposit(userId) > recommendationsRepository.getTotalDebitWithdraw(userId)) &&
                recommendationsRepository.getTotalDebitWithdraw(userId) > 100000) {
            return Optional.of(recommendationDTOMapper.apply(recommendationInfoRepository.getRecommendation(id)));
        }
        return Optional.empty();
    }
}
