package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
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
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //  генерация UUID на стороне БД или GenerationType.UUID генерация UUID на стороне Java
    @JsonView(Views.Response.class)
    private UUID id;

    @JsonView(Views.Request.class)
    private String query;

    @JsonView(Views.Request.class)
    private List<String> arguments;

    @JsonView(Views.Request.class)
    private Boolean negate;

    @ManyToOne
    @JoinColumn(name = "Recommendation_Id")
    @JsonIgnore
    private Recommendation product;
}
