package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RecommendationRuleSet {

    RecommendationDTO getRecommendation(UUID userId);
}
