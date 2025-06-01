package com.skypro.recommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Recommendation {

    @JsonView(Views.Request.class)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Request.class)
    private UUID id;

    @JsonView(Views.Request.class)
    private String description;

    @OneToMany(mappedBy = "products")
    @JsonIgnore
    private List<Rule> rules;
}
