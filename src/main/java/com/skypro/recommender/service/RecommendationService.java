package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }

    public List<RecommendationDTO> getRecommendation(UUID userId) {
        return rules.stream()
                .map(rule -> rule.getRecommendation(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
