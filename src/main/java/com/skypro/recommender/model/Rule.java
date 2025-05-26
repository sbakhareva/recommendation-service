package com.skypro.recommender.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Rule {

    Long id;
    String query;
    List<String> arguments;
    boolean negate;

}
