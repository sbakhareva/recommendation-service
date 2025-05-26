package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Rule {

    @JsonIgnore
    private UUID id;
    private String query;
    private List<String> arguments;
    private boolean negate;
    @JsonIgnore
    private UUID recommendation_id;
}
