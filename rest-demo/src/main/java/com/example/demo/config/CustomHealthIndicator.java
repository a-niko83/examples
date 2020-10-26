package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final WorkingTime workingTime;

    @Override
    public Health health() {
        Health.Builder status;
        if (workingTime.isStarted()) {
            status = Health.up();
        } else {
            status = Health.down();
        }
        return status.build();
    }
}
