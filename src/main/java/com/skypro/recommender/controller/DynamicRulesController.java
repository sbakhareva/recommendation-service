package com.skypro.recommender.controller;

import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create/{recommendation_id}")
    public RecommendationInfo createRule(@RequestBody Rule rule, @PathVariable("recommendation_id") UUID recommendationId) {
        return dynamicRulesService.createRule(rule, recommendationId);
    }

    @GetMapping("/get/{recommendation_id}")
    public RecommendationInfo getRecommendationWithRules(@PathVariable("recommendation_id") UUID recommendationId) {
        return dynamicRulesService.getRecommendationWithRules(recommendationId);
    }

    @DeleteMapping("/delete/{rule_id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("rule_id") UUID ruleId) {
        dynamicRulesService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }
}
