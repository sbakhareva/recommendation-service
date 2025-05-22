package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RecommendationRuleSet {

    Optional<RecommendationDTO> getRecommendation(UUID userId);
}
