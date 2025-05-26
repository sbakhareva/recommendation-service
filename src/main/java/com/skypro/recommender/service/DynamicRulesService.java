package com.skypro.recommender.service;

import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public DynamicRulesService(DynamicRulesRepository dynamicRulesRepository, RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    public String createRule(Rule rule, UUID recommendation_id) {
        dynamicRulesRepository.createRule(rule, recommendation_id);
        return dynamicRulesRepository.getRecommendationWithRules(recommendation_id);
    }
}
