package com.skypro.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.RecommendationInfo;
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

    public RecommendationInfo createRule(Rule rule, UUID recommendationId) {
        try {
            dynamicRulesRepository.createRule(rule, recommendationId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RecommendationInfo(recommendationInfoRepository.getRecommendation(recommendationId),
                dynamicRulesRepository.getRules(recommendationId));
    }

    public RecommendationInfo getRecommendationWithRules(UUID recommendationId) {
        return new RecommendationInfo(
                recommendationInfoRepository.getRecommendation(recommendationId),
                dynamicRulesRepository.getRules(recommendationId)
        );
    }

    public void deleteRule(UUID ruleId) {
        dynamicRulesRepository.deleteRule(ruleId);
    }
}
