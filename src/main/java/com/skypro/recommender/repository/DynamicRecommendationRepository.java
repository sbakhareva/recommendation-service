package com.skypro.recommender.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DynamicRecommendationRepository {


    private final JdbcTemplate jdbcTemplate;

    public DynamicRecommendationRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(value = "userOf", key = "#userId, #productType")
    public boolean isUserOfProduct(UUID userId, String productType) {

        String request = "SELECT EXISTS ( " +
                "SELECT 1 " +
                "FROM transactions t " +
                "JOIN products p ON p.id = t.product_id " +
                "WHERE t.user_id = ? " +
                "AND p.type = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                request,
                Boolean.class,
                userId,
                productType));
    }

    @Cacheable(value = "activeUser", key = "#userId, #productType")
    public boolean isActiveUserOfProduct(UUID userId, String productType) {

        String request = "SELECT COUNT(*) " +
                "FROM transactions t " +
                "JOIN products p ON p.id = t.product_id " +
                "WHERE t.user_id = ? " +
                "AND p.type = ? ";

        Integer count = jdbcTemplate.queryForObject(
                request,
                Integer.class,
                userId,
                productType);

        return count != null && count >= 5;
    }

    @Cacheable(value = "sumCompare", key = "#userId, #queryArgs")
    public boolean compareTransactionSum(UUID userId, List<String> queryArgs) {

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT SUM(amount) ? ? AS result" +
                        "FROM transactions t " +
                        "JOIN products p " +
                        "ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND p.type= ? " +
                        "AND t.type = ? ",
                Boolean.class,
                userId,
                queryArgs.get(2),
                queryArgs.get(3),
                queryArgs.get(0),
                queryArgs.get(1)
        ));
    }

    @Cacheable(value = "sumCompare", key = "#userId, #queryArgs")
    public boolean compareTransactionSumDepositWithdraw(UUID userId, List<String> queryArgs) {

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT SUM(CASE WHEN t.type = 'DEPOSIT' THEN amount ELSE 0 END) ? " +
                            "SUM(CASE WHEN t.type = 'WITHDRAW' THEN amount ELSE 0 END) AS result " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? AND p.type = ?",
                Boolean.class,
                queryArgs.get(0),
                userId,
                queryArgs.get(1)
        ));

    }
}
