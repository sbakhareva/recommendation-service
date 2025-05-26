package com.skypro.recommender.service;

import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        dynamicRulesRepository.createRule(rule, recommendationId);
        return new RecommendationInfo(recommendationInfoRepository.getRecommendation(recommendationId), List.of(rule));
    }

    public RecommendationInfo getRecommendationWithRules(UUID recommendationId) {
        return new RecommendationInfo(
                recommendationInfoRepository.getRecommendation(recommendationId),
                dynamicRulesRepository.getRules(recommendationId)
        );
    }
}
