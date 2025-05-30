package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.RecommendationDTO;
import lombok.*;

import java.util.List;

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

    private RecommendationDTO recommendations;
    private List<Rule> rules;
}
