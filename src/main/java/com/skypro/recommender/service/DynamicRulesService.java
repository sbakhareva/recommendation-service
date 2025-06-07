package com.skypro.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.RuleStatistics;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис, отвечающий за CRUD-операции с динамическими правилами и связанными с ними рекомендациями
 */
@Service
public class DynamicRulesService {

    private final DynamicRulesRepository dynamicRulesRepository;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public DynamicRulesService(DynamicRulesRepository dynamicRulesRepository, RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesRepository = dynamicRulesRepository;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    public Recommendation createRuleByRecommendationId(Rule rule, UUID recommendationId) {
        dynamicRulesRepository.createRuleByRecommendationId(rule, recommendationId);
        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public Recommendation createRecommendationWithRules(Recommendation recommendation) {
        try {
            recommendationInfoRepository.createRecommendationWithRules(recommendation);
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка сериализации списка аргументов!");
        }
        return recommendationInfoRepository.getRecommendationWithRules(recommendation.getId());
    }

    public Recommendation getRecommendationWithRules(UUID recommendationId) {
        return recommendationInfoRepository.getRecommendationWithRules(recommendationId);
    }

    public void deleteRule(UUID ruleId) {
        dynamicRulesRepository.deleteRule(ruleId);
    }

    public List<Rule> getAllRules() {
        return dynamicRulesRepository.getAllRules();
    }

    public RuleStatistics getRuleStatistics() {
        return dynamicRulesRepository.getRulesStatistics();
    }

    public void resetStatistics() {
        dynamicRulesRepository.resetStatistics();
    }
}
