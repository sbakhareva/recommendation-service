package com.skypro.recommender.model.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryObjectDto {

    private String query;
    private List<String> arguments;
    private Boolean negate;
}
