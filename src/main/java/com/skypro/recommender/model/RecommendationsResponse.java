package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.RecommendationDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class RecommendationsResponse {
    private UUID userId;
    private List<RecommendationDTO> recommendations;

    public RecommendationsResponse(UUID userId, List<RecommendationDTO> recommendations) {
        this.userId = userId;
        this.recommendations = recommendations;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<RecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationsResponse that = (RecommendationsResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(recommendations, that.recommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendations);
    }

    @Override
    public String toString() {
        return "RecommendationsResponse" +
                "userId" + userId +
                ", recommendations" + recommendations;
    }
}
