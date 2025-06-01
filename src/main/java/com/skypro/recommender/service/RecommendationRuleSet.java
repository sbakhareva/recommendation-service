package com.skypro.recommender.service;

import com.skypro.recommender.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RecommendationRuleSet {

    Optional<Recommendation> getRecommendation(UUID userId);
}
