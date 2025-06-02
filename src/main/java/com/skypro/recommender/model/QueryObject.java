package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий динамические запросы
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class QueryObject {

    @JsonView(Views.Response.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonView(Views.Request.class)
    private String query;

    @JsonView(Views.Request.class)
    private List<String> arguments;

    @JsonView(Views.Request.class)
    private Boolean negate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    @JsonIgnore
    private Rule rule;
}
