package com.skypro.recommender.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RuleStatistics {

    UUID ruleId;
    int count;
}
