package com.skypro.recommender.model.dto;

import lombok.*;

import java.util.UUID;

/**
 * Класс, описывающий рекомендацию без динамических правил
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationDTO {

    private String name;
    private UUID id;
    private String description;
}
