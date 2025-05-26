package com.skypro.recommender.configuration;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RecommendationsDataSourceConfiguration {

    @Primary
    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(
            @Value("${application.recommendations-db.url}") String recommendationsUrl) {

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "recommendationsInfoDataSource")
    public DataSource recommendationInfoDataSource(
            @Value("${application.recommendations-info.url}") String recommendationsUrl) {

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        return dataSource;
    }

    @Bean(name = "recommendationsInfoJdbcTemplate")
    public JdbcTemplate recommendationsInfoJdbcTemplate(
            @Qualifier("recommendationsInfoDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SpringLiquibase recommendationsInfoLiquibase(
            @Qualifier("recommendationsInfoDataSource") DataSource dataSource) {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/recommendations-info/changelog-master.yml");
        return liquibase;
    }
}
