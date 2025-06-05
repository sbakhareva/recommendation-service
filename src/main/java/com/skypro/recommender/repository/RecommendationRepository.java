package com.skypro.recommender.repository;

import com.skypro.recommender.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID>{
}
