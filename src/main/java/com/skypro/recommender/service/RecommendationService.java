package com.skypro.recommender.service;

import com.skypro.recommender.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private RecommendationsRepository recommendationsRepository;
    private RecommendationRuleSet recommendationRuleSet;
}
