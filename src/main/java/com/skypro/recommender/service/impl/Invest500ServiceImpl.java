package com.skypro.recommender.service.impl;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.service.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Invest500ServiceImpl implements RecommendationRuleSet {

    @Override
    public RecommendationDTO getRecommendation(UUID userId) {
        return null;
    }
}
