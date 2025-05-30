package com.skypro.recommender.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.Rule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class RuleRowMapper implements RowMapper<Rule> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(rs.getObject("id", UUID.class));
        rule.setQuery(rs.getString("query"));
        rule.setNegate(rs.getBoolean("negate"));
        rule.setRecommendation_id(rs.getObject("recommendation_id", UUID.class));
        String argumentsJson = rs.getString("arguments");
        try {
            List<String> arguments = objectMapper.readValue(argumentsJson, new TypeReference<>() {
            });
            rule.setArguments(arguments);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to parse arguments JSON", e);
        }
        return rule;
    }
}
