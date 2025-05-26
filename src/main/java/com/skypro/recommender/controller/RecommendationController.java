package com.skypro.recommender.controller;

import com.skypro.recommender.model.RecommendationsResponse;
import com.skypro.recommender.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{user_id}")
    public RecommendationsResponse getRecommendations(@PathVariable ("user_id") UUID userId) {
        return new RecommendationsResponse(userId,
                recommendationService.getRecommendation(userId));
    }

}
