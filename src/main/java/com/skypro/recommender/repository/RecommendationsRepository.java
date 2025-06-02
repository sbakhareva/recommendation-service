package com.skypro.recommender.repository;

import com.skypro.recommender.model.dto.RecommendationDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий, который работает с базой данных transactions
 */
@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
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

    /**
     * Метод, который проверяет, использует ли пользователь данный продукт
     *
     * @param userId      идентификатор пользователя
     * @param productType тип продукта (DEBIT, CREDIT, SAVING, INVEST)
     */
    //@Cacheable(value = "userOf", key = "#userId")
    public boolean checkIfUserUsesProduct(UUID userId, String productType) {

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

    /**
     * Метод, который проверяется, является ли пользователь активным пользователем данного продукта
     * (у пользователя есть более пяти транзакций по продукту данного типа)
     *
     * @param userId      идентификатор пользователя
     * @param productType тип продукта (DEBIT, CREDIT, SAVING, INVEST)
     */
    //@Cacheable(value = "activeUser", key = "#userId")
    public boolean checkIfUserIsActive(UUID userId, String productType) {

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

    /**
     * Метод, который проверяет сумму всех транзакций по конкретному продукту с константой, установленной в правиле
     *
     * @param userId идентификатор пользователя
     */
   // @Cacheable(value = "sumCompare", key = "#userId")
    public boolean transactionSumCompare(UUID userId,
                                         String productType,
                                         String transactionType,
                                         String operator,
                                         int checksum) {
        String request;
        switch (operator) {
            case ">" -> {
                request = "SELECT CASE WHEN SUM(t.amount) > ? THEN true ELSE false END AS result " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ? ";
            }
            case "<" -> {
                request = "SELECT CASE WHEN SUM(t.amount) < ? THEN true ELSE false END AS result " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ? ";
            }
            case "=" -> {
                request = "SELECT CASE WHEN SUM(t.amount) = ? THEN true ELSE false END AS result " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ? ";
            }
            case ">=" -> {
                request = "SELECT CASE WHEN SUM(t.amount) >= ? THEN true ELSE false END AS result " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ? ";
            }
            case "<=" -> {
                request = "SELECT CASE WHEN SUM(t.amount) <= ? THEN true ELSE false END AS result " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = ? ";
            }
            default -> {
                throw new IllegalArgumentException("Неопознанный оператор " + operator);
            }
        }
        Object[] params = new Object[]{
                checksum,
                userId,
                productType,
                transactionType
        };
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                request,
                Boolean.class,
                params
        ));
    }

    /**
     * Метод, который сравнивает суммы трат и суммы пополнений по определенному продукту
     *
     * @param userId      идентификатор пользователя
     * @param productType тип продукта (DEBIT, CREDIT, SAVING, INVEST)
     * @param operator    оператор сравнения
     */
    //@Cacheable(value = "compareDepositWithdraw", key = "#userId")
    public boolean transactionSumCompareDepositWithdraw(UUID userId,
                                                        String productType,
                                                        String operator) {
        String request;
        switch (operator) {
            case ">" -> {
                request = "SELECT CASE "
                        + "WHEN deposit_sum > withdraw_sum THEN true "
                        + "ELSE false END AS result "
                        + "FROM ("
                        + "SELECT "
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type='DEPOSIT' AND p.type= ?) AS deposit_sum,"
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type= 'WITHDRAW' AND p.type= ?) AS withdraw_sum"
                        + ") sub";
            }
            case "<" -> {
                request = "SELECT CASE "
                        + "WHEN deposit_sum < withdraw_sum THEN true "
                        + "ELSE false END AS result "
                        + "FROM ("
                        + "SELECT "
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type='DEPOSIT' AND p.type= ?) AS deposit_sum,"
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type= 'WITHDRAW' AND p.type= ?) AS withdraw_sum"
                        + ") sub";
            }
            case "=" -> {
                request = "SELECT CASE "
                        + "WHEN deposit_sum = withdraw_sum THEN true "
                        + "ELSE false END AS result "
                        + "FROM ("
                        + "SELECT "
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type='DEPOSIT' AND p.type= ?) AS deposit_sum,"
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type= 'WITHDRAW' AND p.type= ?) AS withdraw_sum"
                        + ") sub";
            }
            case ">=" -> {
                request = "SELECT CASE "
                        + "WHEN deposit_sum >= withdraw_sum THEN true "
                        + "ELSE false END AS result "
                        + "FROM ("
                        + "SELECT "
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type='DEPOSIT' AND p.type= ?) AS deposit_sum,"
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type= 'WITHDRAW' AND p.type= ?) AS withdraw_sum"
                        + ") sub";
            }
            case "<=" -> {
                request = "SELECT CASE "
                        + "WHEN deposit_sum <= withdraw_sum THEN true "
                        + "ELSE false END AS result "
                        + "FROM ("
                        + "SELECT "
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type='DEPOSIT' AND p.type= ?) AS deposit_sum,"
                        + "(SELECT COALESCE(SUM(t.amount), 0) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND t.type= 'WITHDRAW' AND p.type= ?) AS withdraw_sum"
                        + ") sub";
            }
            default -> {
                throw new IllegalArgumentException("Неопознанный оператор " + operator);
            }
        }
        Object[] params = new Object[]{
                userId,
                productType,
                userId,
                productType
        };
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                request,
                Boolean.class,
                params
        ));
    }

    public UUID getUserIdByUsername(String username) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM users " +
                        "WHERE username = ? ",
                UUID.class,
                username
        );
    }

    public String getFirstNameByUsername(String username) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT first_name FROM users " +
                        "WHERE username = ? ",
                String.class,
                username
        );
    }

    public String getLastNameByUsername(String username) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT last_name FROM users " +
                        "WHERE username = ? ",
                String.class,
                username
        );
    }
}
