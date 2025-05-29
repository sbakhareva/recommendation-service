package com.skypro.recommender.controller;

import com.skypro.recommender.model.RecommendationsResponse;
import com.skypro.recommender.service.RecommendationService;
import com.skypro.recommender.service.RecommendationServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationServiceV2 recommendationServiceV2;

    public RecommendationController(RecommendationService recommendationService, RecommendationServiceV2 recommendationServiceV2) {
        this.recommendationService = recommendationService;
        this.recommendationServiceV2 = recommendationServiceV2;
    }

//    @GetMapping("/{user_id}")
//    public RecommendationsResponse getRecommendations(@PathVariable ("user_id") UUID userId) {
//        return new RecommendationsResponse(userId,
//                recommendationService.getRecommendation(userId));
//    }

    @GetMapping("/{user_id}")
    public RecommendationsResponse getRecommendation(@PathVariable("user_id") UUID userId) {
        return new RecommendationsResponse(userId,
                recommendationServiceV2.getRecommendations(userId));
    }
}
