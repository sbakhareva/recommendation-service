package com.skypro.recommender.controller;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import com.skypro.recommender.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RecommendationService recommendationService;
    @MockitoBean
    private RecommendationInfoRepository recommendationInfoRepository;
    @MockitoBean
    private RecommendationsRepository recommendationsRepository;

    @Test
    void getRecommendation() throws Exception {
        UUID userId = UUID.fromString("d4a4d619-9a0c-4fc5-b0cb-76c49409546b");

        String name = "рекомендация";
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
        String description = "описание";
        RecommendationDTO recommendation = new RecommendationDTO(name, recommendationId, description);

        when(recommendationsRepository.checkIfUserHasTransactionTypeDebit(userId)).thenReturn(true);
        when(recommendationsRepository.getTotalDebitDeposit(userId)).thenReturn(74534);
        when(recommendationsRepository.getTotalDebitWithdraw(userId)).thenReturn(53467);

        when(recommendationInfoRepository.getRecommendationDescription(recommendationId)).thenReturn(description);
        when(recommendationInfoRepository.getRecommendationName(recommendationId)).thenReturn(name);

        when(recommendationService.getRecommendation(userId)).thenReturn(List.of(recommendation));
//
//        mockMvc.perform(get("/school/student/get?id=1"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(s.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(s.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(s.getAge()));
    }
}
