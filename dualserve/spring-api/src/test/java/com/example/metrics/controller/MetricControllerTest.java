package com.example.metrics.controller;

import com.example.metrics.entity.Alert;
import com.example.metrics.service.MetricService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricController.class)
class MetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricService metricService;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void createMetric() throws Exception {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setMetricId(1L);
        alert.setAnomalyScore(0.5);
        alert.setLabel("anomaly");

        when(metricService.processMetric(any())).thenReturn(alert);

        mockMvc.perform(post("/metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
}