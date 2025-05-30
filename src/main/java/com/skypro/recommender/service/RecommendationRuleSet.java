package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface RecommendationRuleSet {

    Optional<ProductDTO> getRecommendation(UUID userId);
}
