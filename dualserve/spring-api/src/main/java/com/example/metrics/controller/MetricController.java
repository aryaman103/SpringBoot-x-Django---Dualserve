package com.example.metrics.controller;

import com.example.metrics.entity.Alert;
import com.example.metrics.service.MetricService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MetricController {

    private final MetricService metricService;
    private final SimpMessagingTemplate messagingTemplate;

    public MetricController(MetricService metricService, SimpMessagingTemplate messagingTemplate) {
        this.metricService = metricService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/metrics")
    public Alert createMetric(@RequestBody Map<String, Object> payload) {
        Alert alert = metricService.processMetric(payload);
        messagingTemplate.convertAndSend("/topic/alerts", alert);
        return alert;
    }
}