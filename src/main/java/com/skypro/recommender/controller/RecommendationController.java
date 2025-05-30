package com.skypro.recommender.controller;

import com.skypro.recommender.model.RecommendationsResponse;
import com.skypro.recommender.service.DynamicRecommendationService;
import com.skypro.recommender.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final DynamicRecommendationService dynamicRecommendationService;

    public RecommendationController(RecommendationService recommendationService,
                                    DynamicRecommendationService dynamicRecommendationService) {
        this.recommendationService = recommendationService;
        this.dynamicRecommendationService = dynamicRecommendationService;
    }

    @GetMapping("/{user_id}")
    public RecommendationsResponse getRecommendations(@PathVariable ("user_id") UUID userId) {
        return new RecommendationsResponse(userId,
                //recommendationService.getRecommendation(userId));
                dynamicRecommendationService.getRecommendation(userId));
    }

}
