package com.skypro.recommender.model.dto;

import com.skypro.recommender.model.Recommendation;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecommendationDTOMapper implements Function<Recommendation, RecommendationDTO> {

    @Override
    public RecommendationDTO apply(Recommendation recommendation) {
        return new RecommendationDTO(
                recommendation.getId(),
                recommendation.getName(),
                recommendation.getDescription()
        );
    }
}
