package com.skypro.recommender.model.dto;

import com.skypro.recommender.model.Rule;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationDTO {

    private String name;
    private UUID id;
    private String text;
    private List<Rule> rules;
}
