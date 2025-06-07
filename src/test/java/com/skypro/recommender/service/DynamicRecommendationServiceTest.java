package com.skypro.recommender.service;

import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.model.dto.RecommendationDTOMapper;
import com.skypro.recommender.repository.DynamicRulesRepository;
import com.skypro.recommender.repository.RecommendationInfoRepository;
import com.skypro.recommender.repository.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRecommendationServiceTest {

    @Mock
    private DynamicRulesRepository dynamicRulesRepository;
    @Mock
    private RecommendationsRepository recommendationsRepository;
    @Mock
    private RecommendationInfoRepository recommendationInfoRepository;
    @Mock
    private RecommendationDTOMapper recommendationDTOMapper;
    @InjectMocks
    private DynamicRecommendationService dynamicRecommendationService;

    @Test
    void getRecommendationsTest() {
        UUID userId = UUID.randomUUID();

        UUID recommendationId = UUID.randomUUID();
        UUID recommendationId2 = UUID.randomUUID();
        UUID recommendationId3 = UUID.randomUUID();

        Rule rule = new Rule();
        rule.setRecommendation_id(recommendationId);
        rule.setNegate(false);
        Rule rule1 = new Rule();
        rule1.setRecommendation_id(recommendationId2);
        rule1.setNegate(false);
        Rule rule2 = new Rule();
        rule2.setRecommendation_id(recommendationId3);
        rule2.setNegate(false);

        Recommendation rec = new Recommendation();
        rec.setRules(List.of(rule));
        Recommendation rec1 = new Recommendation();
        rec1.setRules(Collections.emptyList());
        Recommendation rec2 = new Recommendation();
        rec2.setRules(List.of(rule1));
        Recommendation rec3 = new Recommendation();
        rec3.setRules(List.of(rule2));

        List<Recommendation> recommendations = List.of(rec, rec1, rec2, rec3);
        when(recommendationInfoRepository.getAllRecommendations()).thenReturn(recommendations);

        when(recommendationDTOMapper.apply(any())).thenAnswer(invocation -> {
            Recommendation r = invocation.getArgument(0);
            return new RecommendationDTO(UUID.randomUUID(), "название", "описание");
        });

        DynamicRecommendationService spy = Mockito.spy(dynamicRecommendationService);

        doReturn(false).when(spy).checkRules(any(UUID.class), eq(rule));
        doReturn(true).when(spy).checkRules(any(UUID.class), eq(rule1));
        doReturn(false).when(spy).checkRules(any(UUID.class), eq(rule2));

        List<RecommendationDTO> result = spy.getRecommendations(userId);

        assertNotNull(result);
        System.out.println(result);
        assertEquals(3, result.size());

        verify(spy).checkRules(any(UUID.class), eq(rule));
        verify(spy).checkRules(any(UUID.class), eq(rule1));
    }
}