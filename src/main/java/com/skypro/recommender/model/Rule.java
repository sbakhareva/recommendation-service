package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий правила
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "rule")
    private List<QueryObject> queryObjects;

    @OneToOne(mappedBy = "rule")
    @JsonIgnore
    private Recommendation recommendation;
}
