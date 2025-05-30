package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий динамические правила
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Rule {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String query;
    private List<String> arguments;
    private boolean negate;
    @JsonIgnore
    private UUID recommendation_id;
}
