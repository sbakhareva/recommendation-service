package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий динамические правила
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Rule {

    @JsonIgnore
    private UUID id;
    private String query;
    private List<String> arguments;
    private Boolean negate;
    @JsonIgnore
    private UUID recommendation_id;

    public Rule(String query, List<String> arguments, boolean negate, UUID recommendation_id) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
        this.recommendation_id = recommendation_id;
    }
}
