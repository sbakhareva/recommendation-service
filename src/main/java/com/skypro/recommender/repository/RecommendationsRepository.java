package com.skypro.recommender.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate" ) JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTotalDebitDeposit(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) " +
                        "FROM transactions t " +
                        "JOIN products p " +
                        "ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND t.type = 'DEPOSIT' " +
                        "AND p.type= 'DEBIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalDebitWithdraw(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND t.type = 'WITHDRAW' " +
                        "AND p.type = 'DEBIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalSavingDeposit(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND t.type = 'DEPOSIT' " +
                        "AND p.type = 'SAVING'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public Boolean checkIfUserHasTransactionTypeInvest(UUID userId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS ( " +
                        "SELECT 1 " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND p.type = 'INVEST')",
                Boolean.class,
                userId);
    }

    public Boolean checkIfUserHasTransactionTypeDebit(UUID userId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS ( " +
                        "SELECT 1 " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND p.type = 'DEBIT')",
                Boolean.class,
                userId);
    }

    public Boolean checkIfUserHasTransactionTypeCredit(UUID userId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS ( " +
                        "SELECT 1 " +
                        "FROM transactions t " +
                        "JOIN products p ON p.id = t.product_id " +
                        "WHERE t.user_id = ? " +
                        "AND p.type = 'CREDIT')",
                Boolean.class,
                userId);
    }
}
