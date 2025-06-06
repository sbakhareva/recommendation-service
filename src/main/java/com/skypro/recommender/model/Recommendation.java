package com.skypro.recommender.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, описывающий рекомендацию без динамических правил (для вывода пользователю)
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Recommendation {

    private String name;
    private UUID id;
    private String description;
    private List<Rule> rules;
}
