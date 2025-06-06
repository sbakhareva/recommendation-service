package com.skypro.recommender.controller;

import com.skypro.recommender.exception.NoSuitableRecommendationsException;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.service.DynamicRecommendationService;
import com.skypro.recommender.service.StaticRecommendationService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Контроллер, подбирающий рекомендации для пользователя
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final StaticRecommendationService staticRecommendationService;
    private final DynamicRecommendationService dynamicRecommendationService;

    public RecommendationController(StaticRecommendationService staticRecommendationService, DynamicRecommendationService dynamicRecommendationService) {
        this.staticRecommendationService = staticRecommendationService;
        this.dynamicRecommendationService = dynamicRecommendationService;
    }

    @GetMapping("/{user_id}")
    public RecommendationsResponse getRecommendation(@PathVariable("user_id") UUID userId) {
        List<RecommendationDTO> recommendationsByStaticRules = staticRecommendationService.getRecommendations(userId);
        List<RecommendationDTO> recommendationByDynamicRules = dynamicRecommendationService.getRecommendations(userId);

        List<RecommendationDTO> recommendations = Stream
                .concat(recommendationByDynamicRules.stream(), recommendationsByStaticRules.stream())
                .distinct()
                .toList();
        if (recommendations.isEmpty()) {
            throw new NoSuitableRecommendationsException();
        }
        return new RecommendationsResponse(userId, recommendations);
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
