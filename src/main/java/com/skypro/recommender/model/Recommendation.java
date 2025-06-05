package com.skypro.recommender.model;

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
    private UUID productId;

    @JsonView(Views.Request.class)
    private String productName;

    @JsonView(Views.Request.class)
    private String productText;

    @OneToOne(mappedBy = "recommendation")
    @JsonView(Views.Request.class)
    private Rule rule;
}
