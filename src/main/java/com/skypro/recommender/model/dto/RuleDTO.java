package com.skypro.recommender.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.skypro.recommender.model.Views;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RuleDTO {

    @JsonView(Views.Response.class)
    private UUID id;

    @JsonView(Views.Request.class)
    private String query;

    @JsonView(Views.Request.class)
    private List<String> arguments;

    @JsonView(Views.Request.class)
    private Boolean negate;

    public RuleDTO() {
        this.id = UUID.randomUUID();
    }

    public RuleDTO(String query, List<String> arguments, Boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }
}
