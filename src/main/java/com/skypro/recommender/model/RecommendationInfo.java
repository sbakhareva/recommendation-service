package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.RecommendationDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий ответ для работы с динамическими правилами (не для пользователя)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationInfo {

    private String name;
    private UUID id;
    private String description;
    private List<Rule> rules;
}
