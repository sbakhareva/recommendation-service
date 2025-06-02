package com.skypro.recommender.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationDTO {

    private UUID recommendationId;
    private String name;
    private String description;
}
