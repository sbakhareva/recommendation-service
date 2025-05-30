package com.skypro.recommender.model.dto;

import com.skypro.recommender.model.Rule;
import lombok.*;

import java.util.List;
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
    private List<Rule> rules;
}
