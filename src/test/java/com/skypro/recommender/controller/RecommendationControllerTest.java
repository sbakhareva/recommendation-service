package com.skypro.recommender.controller;

import com.skypro.recommender.model.dto.ProductDTO;
import com.skypro.recommender.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RecommendationService recommendationService;

    @Test
    void getRecommendation() throws Exception {
        UUID userId = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");

        String name = "рекомендация";
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
        String description = "описание";
        ProductDTO recommendation = new ProductDTO(name, recommendationId, description);

        when(recommendationService.getRecommendation(userId)).thenReturn(List.of(recommendation));

        mockMvc.perform(get("/recommendation/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.recommendations[0].id").value(recommendationId.toString()))
                .andExpect(jsonPath("$.recommendations[0].name").value(recommendation.getName()))
                .andExpect(jsonPath("$.recommendations[0].text").value(recommendation.getDescription()));
    }
}
