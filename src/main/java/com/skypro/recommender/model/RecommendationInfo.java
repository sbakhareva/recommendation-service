package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.RecommendationDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationInfo {

    private RecommendationDTO recommendationDTO;
    private List<Rule> rules;
}
