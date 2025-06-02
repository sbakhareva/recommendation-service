package com.skypro.recommender.controller;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.service.RecommendationService;
import com.skypro.recommender.service.RecommendationServiceV2;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер, подбирающий рекомендации для пользователя
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationServiceV2 recommendationServiceV2;

    public RecommendationController(RecommendationService recommendationService, RecommendationServiceV2 recommendationServiceV2) {
        this.recommendationService = recommendationService;
        this.recommendationServiceV2 = recommendationServiceV2;
    }


    @GetMapping("/{user_id}")
    public RecommendationsResponse getRecommendation(@PathVariable("user_id") UUID userId) {
        return new RecommendationsResponse(userId,
                recommendationServiceV2.getRecommendations(userId)
                //recommendationService.getRecommendations(userId)
        );
    }

}

/**
 * Класс, описывающий ответ сервера пользователю, включающий переданный user_id (необходимо по требуемому API)
 */
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
class RecommendationsResponse {

    private UUID userId;
    private List<RecommendationDTO> recommendations;
}
