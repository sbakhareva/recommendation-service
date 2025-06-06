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
public class TopSavingServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;
    private final RecommendationDTOMapper recommendationDTOMapper;

    public TopSavingServiceImpl(RecommendationsRepository recommendationsRepository,
                                RecommendationInfoRepository recommendationInfoRepository,
                                RecommendationDTOMapper recommendationDTOMapper) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
        this.recommendationDTOMapper = recommendationDTOMapper;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {

        UUID id = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

        if (recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId) &&
                (recommendationsRepository.getTotalDebitDeposit(userId) >= 50000 || recommendationsRepository.getTotalSavingDeposit(userId) >= 50000) &&
                (recommendationsRepository.getTotalDebitDeposit(userId) > recommendationsRepository.getTotalDebitWithdraw(userId))) {
            return Optional.of(recommendationDTOMapper.apply(recommendationInfoRepository.getRecommendation(id)));
        }
        return Optional.empty();
    }
}