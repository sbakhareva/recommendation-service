package com.skypro.recommender.model.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationDTO {

    private String name;
    private UUID id;
    private String text;
}
