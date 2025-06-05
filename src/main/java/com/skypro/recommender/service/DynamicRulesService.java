package com.skypro.recommender.service;

import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRulesService {

    private final RecommendationRepository recommendationRepository;
    private final RuleRepository ruleRepository;
    private final QueryObjectRepository queryObjectRepository;

    public DynamicRulesService(RecommendationRepository recommendationRepository,
                               RuleRepository ruleRepository,
                               QueryObjectRepository queryObjectRepository) {
        this.recommendationRepository = recommendationRepository;
        this.ruleRepository = ruleRepository;
        this.queryObjectRepository = queryObjectRepository;
    }

    public Recommendation createRule(Recommendation recommendation) {

        Rule rule = recommendation.getRule();
        recommendation.setRule(rule);
        rule.setRecommendation(recommendation);

        if (rule != null) {
            rule.getQueryObjects().forEach(q -> q.setRule(rule));
        }

        return recommendationRepository.save(recommendation);
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
