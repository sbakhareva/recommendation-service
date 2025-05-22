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

    public int getTotalDebitDeposit(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_debit_deposit " +
                        "FROM products_transactions pt " +
                        "WHERE pt.user_id = ? " +
                        "AND product_type = 'DEBIT' " +
                        "AND transaction_type = 'DEPOSIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalDebitWithdraw(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_debit_withdraw " +
                        "FROM products_transactions pt " +
                        "WHERE pt.user_id = ? " +
                        "AND product_type = 'DEBIT' " +
                        "AND transaction_type = 'WITHDRAW'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int getTotalSavingDeposit(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) as total_saving_deposit " +
                        "FROM products_transactions pt " +
                        "WHERE pt.user_id = ? " +
                        "AND product_type = 'SAVING' " +
                        "AND transaction_type = 'DEPOSIT'",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int checkIfUserHasTransactionTypeInvest(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT EXISTS (" +
                        "SELECT 1 FROM products_transactions " +
                        "WHERE user_id = ? " +
                        "AND product_type = 'INVEST'" +
                        ") as has_invest",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public int checkIfUserHasTransactionTypeDebit(UUID userId) {
        // функция вернет 1, если у пользователя есть хотя бы один продукт типа DEBIT,
        // или 0, если такого продукта нет
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (" +
                        "SELECT 1 FROM products_transactions " +
                        "WHERE user_id = ? " +
                        "AND product_type = 'DEBIT'" +
                        ") as has_debit",
                Integer.class,
                userId);
    }

    public int checkIfUserHasTransactionTypeCredit(UUID userId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (" +
                        "SELECT 1 FROM products_transactions " +
                        "WHERE user_id = ? " +
                        "AND product_type = 'CREDIT'" +
                        ") as has_credit",
                Integer.class,
                userId);
    }
}
