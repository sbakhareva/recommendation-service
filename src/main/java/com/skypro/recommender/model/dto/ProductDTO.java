package com.skypro.recommender.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.skypro.recommender.model.Views;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductDTO {

    @JsonView(Views.Request.class)
    private String name;

    @JsonView(Views.Request.class)
    private UUID id;

    @JsonView(Views.Request.class)
    private String description;

    @JsonView(Views.Request.class)
    private List<RuleDTO> rules;
}
