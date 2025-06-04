package com.skypro.recommender.service;

import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.QueryObject;
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

    public Recommendation createRule(Recommendation rule) {

            dynamicRulesRepository.createRule();

        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public Recommendation getRecommendationWithRules(UUID recommendationId) {
        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public void deleteRule(String ruleId) {
        dynamicRulesRepository.deleteRule(UUID.fromString (ruleId));
    }

    public List<Recommendation> getAllRules() {

    }
}
