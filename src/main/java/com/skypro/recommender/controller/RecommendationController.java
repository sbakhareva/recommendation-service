package com.skypro.recommender.controller;

import com.skypro.recommender.model.dto.RecommendationDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @GetMapping
    public String getRecommendations(@RequestParam UUID userId) {
        return null;
    }
}
