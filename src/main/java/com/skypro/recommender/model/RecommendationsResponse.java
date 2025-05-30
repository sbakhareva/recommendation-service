package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.RecommendationDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий ответ сервера пользователю
 */
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationsResponse {

    private UUID userId;
    private List<RecommendationDTO> recommendations;
}
