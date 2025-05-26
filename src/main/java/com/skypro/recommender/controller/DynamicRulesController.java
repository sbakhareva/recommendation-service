package com.skypro.recommender.controller;

import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.service.DynamicRulesService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRulesController {

    private final DynamicRulesService dynamicRulesService;
    private final RecommendationInfoRepository recommendationInfoRepository;

    public DynamicRulesController(DynamicRulesService dynamicRulesService, RecommendationInfoRepository recommendationInfoRepository) {
        this.dynamicRulesService = dynamicRulesService;
        this.recommendationInfoRepository = recommendationInfoRepository;
    }

    @PostMapping("/{recommendation_id}")
    public String createRule(@RequestBody Rule rule, @PathVariable("recommendation_id") UUID recommendation_id) {
        return dynamicRulesService.createRule(rule, recommendation_id);
    }
}
