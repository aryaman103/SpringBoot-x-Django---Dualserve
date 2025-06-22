package com.example.metrics.service;

import com.example.metrics.entity.Alert;
import com.example.metrics.entity.Metric;
import com.example.metrics.repository.AlertRepository;
import com.example.metrics.repository.MetricRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Service
public class MetricService {

    private final MetricRepository metricRepository;
    private final AlertRepository alertRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String djangoMlUrl;

    public MetricService(MetricRepository metricRepository, AlertRepository alertRepository,
            RestTemplate restTemplate, ObjectMapper objectMapper,
            @Value("${DJANGO_ML_URL}") String djangoMlUrl) {
        this.metricRepository = metricRepository;
        this.alertRepository = alertRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.djangoMlUrl = djangoMlUrl;
    }

    public Alert processMetric(Map<String, Object> payload) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            Metric metric = new Metric();
            metric.setTs(Instant.now());
            metric.setJsonPayload(jsonPayload);
            metric = metricRepository.save(metric);

            // Call Django ML service
            ScoreResponse scoreResponse = restTemplate.postForObject(djangoMlUrl, Map.of("payload", payload),
                    ScoreResponse.class);

            Alert alert = new Alert();
            alert.setMetricId(metric.getId());
            alert.setAnomalyScore(scoreResponse.getScore());
            alert.setLabel(scoreResponse.getLabel());
            return alertRepository.save(alert);
        } catch (Exception e) {
            throw new RuntimeException("Error processing metric", e);
        }
    }

    public static class ScoreResponse {
        private double score;
        private String label;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}