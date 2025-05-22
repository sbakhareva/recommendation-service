package com.skypro.recommender.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTotalDebitDeposit(UUID userId){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_debit_deposit FROM products_transactions pt WHERE pt.user_id = ? AND product_type = 'DEBIT' AND transaction_type = 'DEPOSIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalDebitWithdraw(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_debit_withdraw FROM products_transactions pt WHERE pt.user_id = ? AND product_type = 'DEBIT' AND transaction_type = 'WITHDRAW'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalSavingDeposit(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_saving_deposit FROM products_transactions pt WHERE pt.user_id = ? AND product_type = 'SAVING' AND transaction_type = 'DEPOSIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalSavingWithdraw(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_saving_withdraw FROM products_transactions pt WHERE pt.user_id = ? AND product_type = 'SAVING' AND transaction_type = 'WITHDRAW'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int checkIfUserHasTransactionTypeInvest(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_invest_deposit FROM products_transactions pt WHERE pt.user_id = ? AND product_type = 'INVEST' AND transaction_type IN ('DEPOSIT', 'WITHDRAW')",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }
}
