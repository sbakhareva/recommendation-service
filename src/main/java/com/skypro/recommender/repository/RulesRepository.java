package com.skypro.recommender.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.dto.ProductDTO;
import com.skypro.recommender.model.dto.RuleDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RulesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public RulesRepository(
            @Qualifier("recommendationsInfoJdbcTemplate") JdbcTemplate jdbcTemplate,
            ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public void createProduct(ProductDTO product) throws JsonProcessingException {
        jdbcTemplate.update(
                "INSERT INTO recommendations " +
                        "(id, name, description) " +
                        "VALUES (?, ?, ?)",
                product.getId(),
                product.getName(),
                product.getDescription());

        for (RuleDTO rule : product.getRules()) {
            jdbcTemplate.update(
                    "INSERT INTO rules " +
                            "(id, query, arguments, negate, recommendation_id) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    rule.getId(),
                    rule.getQuery(),
                    objectMapper.writeValueAsString(rule.getArguments()),
                    rule.getNegate(),
                    product.getId()
            );
        }
    }

    public List<ProductDTO> getAllProducts() {

        Map<UUID, ProductDTO> productMap = new HashMap<>();

        jdbcTemplate.query(
                "SELECT p.id AS product_id, p.name, p.description, r.id  AS rule_id, r.query, r.arguments, r.negate " +
                        "FROM recommendations p " +
                        "LEFT JOIN rules r ON p.id = r.recommendation_id",
                (response) -> {
                    UUID productId = UUID.fromString(response.getString("product_id"));

                    ProductDTO product = productMap.getOrDefault(productId, new ProductDTO());
                    product.setId(productId);
                    product.setName(response.getString("name"));
                    product.setDescription(response.getString("description"));

                    if (response.getString("rule_id") != null) {
                        RuleDTO rule = new RuleDTO();
                        rule.setId(UUID.fromString(response.getString("rule_id")));
                        rule.setQuery(response.getString("query"));
                        try {
                            rule.setArguments(objectMapper.readValue(response.getString("arguments"), List.class));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("Ошибка при парсинге аргументов", e);
                        }
                        rule.setNegate(response.getBoolean("negate"));

                        if (product.getRules() == null) {
                            product.setRules(new ArrayList<>());
                        }
                        product.getRules().add(rule);
                    }
                    productMap.put(productId, product);
                });
        return new ArrayList<>(productMap.values());
    }

    public void deleteProductById(String productId) {
        jdbcTemplate.update(
                "DELETE FROM rules " +
                        "WHERE recommendation_id = ?",
                productId);
        jdbcTemplate.update(
                "DELETE FROM recommendations " +
                        "WHERE id = ?",
                productId);
    }
}
