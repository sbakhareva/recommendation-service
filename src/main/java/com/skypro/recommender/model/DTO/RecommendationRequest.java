package com.skypro.recommender.model.DTO;

import lombok.*;

import java.util.UUID;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecommendationRequest {

    private String productName;
    private UUID productId;
    private String productText;
    private List<QueryObjectDto> rule;
}