package com.skypro.recommender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DynamicRulesServiceTest {

    @Mock
    private DynamicRulesRepository dynamicRulesRepository;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @InjectMocks
    private DynamicRulesService dynamicRulesService;

    @Test
    void createRuleByRecommendationIdTest() {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргумент"), true, recommendationId);

        dynamicRulesService.createRuleByRecommendationId(rule, recommendationId);
        verify(dynamicRulesRepository).createRuleByRecommendationId(rule, recommendationId);
    }

    @Test
    void createRecommendationWithRulesTest() throws JsonProcessingException {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("название", List.of("аргумент"), true, recommendationId);
        Recommendation recommendation = new Recommendation("название", recommendationId, "описание", List.of(rule));

        dynamicRulesService.createRecommendationWithRules(recommendation);
        verify(recommendationInfoRepository).createRecommendationWithRules(recommendation);
    }

    @Test
    void getRecommendationWithRulesTest() throws JsonProcessingException {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргумент"), true, recommendationId);
        Recommendation recommendation = new Recommendation("рекомендация", recommendationId, "описание", List.of(rule));

        dynamicRulesService.createRecommendationWithRules(recommendation);
        verify(recommendationInfoRepository).createRecommendationWithRules(recommendation);

        when(recommendationInfoRepository.getRecommendationWithRules(any())).thenReturn(recommendation);

        Recommendation response = dynamicRulesService.getRecommendationWithRules(recommendationId);
        assertEquals(response.getId().toString(), (recommendationId.toString()));
        assertEquals(response.getName(), recommendation.getName());
        assertEquals(response.getDescription(), recommendation.getDescription());
    }

    @Test
    void deleteRuleTest() {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргумент"), true, recommendationId);

        dynamicRulesService.createRuleByRecommendationId(rule, recommendationId);
        verify(dynamicRulesRepository).createRuleByRecommendationId(rule, recommendationId);

        dynamicRulesService.deleteRule(rule.getId());
        verify(dynamicRulesRepository).deleteRule(rule.getId());
    }

    @Test
    void getAllRulesTest() {
        UUID recommendationId = UUID.randomUUID();
        Rule rule = new Rule("правило", List.of("аргумент"), true, recommendationId);
        Rule rule1 = new Rule("query", List.of("argument"), false, recommendationId);

        dynamicRulesService.createRuleByRecommendationId(rule, recommendationId);
        dynamicRulesService.createRuleByRecommendationId(rule1, recommendationId);
        when(dynamicRulesRepository.getAllRules()).thenReturn(List.of(rule, rule1));

        List<Rule> rules = dynamicRulesService.getAllRules();
        assertEquals(rules.size(), 2);
        assertEquals(rules.get(0), rule);
        assertEquals(rules.get(1), rule1);
    }
}