package com.skypro.recommender.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StatisticItem {

    UUID ruleId;
    int count;
}
