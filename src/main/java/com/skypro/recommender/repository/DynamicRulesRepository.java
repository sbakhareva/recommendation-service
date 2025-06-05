package com.skypro.recommender.repository;

import com.skypro.recommender.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий, который работает с базой данных recommendation-info, в основном с таблицей rules
 */

public interface DynamicRulesRepository extends JpaRepository<Rule, UUID> {


}
