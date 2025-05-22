package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Invest500ServiceImpl implements RecommendationRuleSet {

    private final RecommendationsRepository recommendationsRepository;

    public Invest500ServiceImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public RecommendationDTO getRecommendation(UUID userId) {
        return null;
    }
}
