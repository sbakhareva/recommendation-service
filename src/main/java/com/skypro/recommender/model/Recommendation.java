package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Request.class)
    private UUID id;

    @JsonView(Views.Request.class)
    private String name;

    @JsonView(Views.Request.class)
    private String description;

    @OneToOne(mappedBy = "recommendation")
    @JoinColumn(name = "rule_id") // Recommendation владеет отношением
    @JsonView(Views.Request.class)
    private Rule rule;
}
