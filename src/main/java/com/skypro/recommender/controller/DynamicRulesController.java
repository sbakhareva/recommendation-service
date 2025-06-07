package com.skypro.recommender.controller;

import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.RuleStatistics;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы непосредственно с динамическими правилами: их сохранение, просмотр и удаление
 */
@RestController
@RequestMapping("/rule")
public class DynamicRulesController {

    private final DynamicRulesService dynamicRulesService;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public DynamicRulesController(DynamicRulesService dynamicRulesService, RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesService = dynamicRulesService;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    /**
     * Метод, который позволяет добавить к уже существующей в базе данных рекомендации новые правила
     * @param rule новое правило
     * @param recommendationId id существующей рекомендации
     * @return рекомендация с новым правилом в списке
     */
    @PostMapping("/{recommendation_id}")
    public Recommendation createRule(@RequestBody Rule rule, @PathVariable("recommendation_id") UUID recommendationId) {
        return dynamicRulesService.createRuleByRecommendationId(rule, recommendationId);
    }

    /**
     * Метод, создающий в базе данных новую рекомендацию с правилами
     * @param recommendation рекомендация вместе со списком правил
     * @return созданная рекомендация со списком правил
     */
    @PostMapping
    public Recommendation createRecommendationWithRules(@RequestBody Recommendation recommendation) {
        dynamicRulesService.createRecommendationWithRules(recommendation);
        return recommendationInfoRepository.getRecommendationWithRules(recommendation.getId());
    }

    @GetMapping("/{recommendation_id}")
    public Recommendation getRecommendationWithRules(@PathVariable("recommendation_id") UUID recommendationId) {
        return dynamicRulesService.getRecommendationWithRules(recommendationId);
    }

    @DeleteMapping("/{rule_id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("rule_id") UUID ruleId) {
        dynamicRulesService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Rule> getAllRules() {
        return dynamicRulesService.getAllRules();
    }

    @GetMapping("/stats")
    public RuleStatistics getStats() {
        return dynamicRulesService.getRuleStatistics();
    }

    @PostMapping("/reset_stats")
    public RuleStatistics resetStats() {
        dynamicRulesService.resetStatistics();
        return dynamicRulesService.getRuleStatistics();
    }
}

