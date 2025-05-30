package com.skypro.recommender.model;

import com.skypro.recommender.model.dto.ProductDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RecommendationsResponse {

    private UUID userId;
    private List<ProductDTO> recommendations;
}
