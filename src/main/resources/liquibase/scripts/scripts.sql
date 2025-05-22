-- liquibase formatted sql

-- changeset sbakhareva:1
CREATE TABLE PRODUCTS_TRANSACTIONS AS
SELECT
    t.id,
    t.product_id,
    t.user_id,
    t.type AS transaction_type,
    t.amount,
    p.type AS product_type,
    p.name
FROM
    transactions t
JOIN
    products p ON t.product_id = p.id;