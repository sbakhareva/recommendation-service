package com.skypro.recommender.model.dto;

import java.util.UUID;

public record RecommendationDTO(
        UUID id,
        String name,
        String text
) {
}
