package ru.epatko.demo.actuator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatusEndpointService implements HealthIndicator {

    private MetricsEndpoint metricsEndpoint;
    @Value("${java.rmi.server.hostname}")
    private String serverName;

    @Autowired
    public void setMetricsEndpoint(MetricsEndpoint metricsEndpoint) {
        this.metricsEndpoint = metricsEndpoint;
    }

    @Override
    public Health health() {
        Map<String, Object> statusMap = new HashMap<>();

        MetricResponse response = metricsEndpoint.metric("process.uptime", null);
        long uptimeMiliseconds = (long) (response.getMeasurements().get(0).getValue() * 1000);
        long millis = uptimeMiliseconds % 1000;
        long second = (uptimeMiliseconds / 1000) % 60;
        long minute = (uptimeMiliseconds / (1000 * 60)) % 60;
        long hour = (uptimeMiliseconds / (1000 * 60 * 60)) % 24;
        long days = uptimeMiliseconds / (1000 * 60 * 60 * 24);
        String formatedUptime = String.format("%d.%02d:%02d:%02d.%03d", days, hour, minute, second, millis);

        statusMap.put("serverName", serverName);
        statusMap.put("uptime", formatedUptime);
        return Health.up().withDetails(statusMap).build();
    }
}
