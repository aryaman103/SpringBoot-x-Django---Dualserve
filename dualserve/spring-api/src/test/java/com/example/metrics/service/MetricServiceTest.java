package com.example.metrics.service;

import com.example.metrics.entity.Alert;
import com.example.metrics.entity.Metric;
import com.example.metrics.repository.AlertRepository;
import com.example.metrics.repository.MetricRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class MetricServiceTest {

    @InjectMocks
    private MetricService metricService;

    @Mock
    private MetricRepository metricRepository;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        metricService = new MetricService(metricRepository, alertRepository, restTemplate, objectMapper,
                "http://test-url");
    }

    @Test
    void processMetric() throws Exception {
        Map<String, Object> payload = Collections.singletonMap("key", "value");
        Metric metric = new Metric();
        metric.setId(1L);
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setMetricId(1L);

        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(metricRepository.save(any(Metric.class))).thenReturn(metric);
        when(restTemplate.postForObject(eq("http://test-url"), any(), any()))
                .thenReturn(new MetricService.ScoreResponse());
        when(alertRepository.save(any(Alert.class))).thenReturn(alert);

        Alert result = metricService.processMetric(payload);

        assertEquals(1L, result.getId());
    }
}