package com.skypro.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.RuleStatistics;
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
        try {
            dynamicRulesRepository.createRule(rule, recommendationId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public RecommendationInfo getRecommendationWithRules(UUID recommendationId) {
        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public void deleteRule(UUID ruleId) {
        dynamicRulesRepository.deleteRule(ruleId);
    }

    public List<Rule> getAllRules() {
        return dynamicRulesRepository.getAllRules();
    }

    public List<RuleStatistics> getRuleStatistics() {
        return dynamicRulesRepository.getRulesStatistics();
    }

    public void resetStatistics() {
        dynamicRulesRepository.resetStatistics();
    }
}
