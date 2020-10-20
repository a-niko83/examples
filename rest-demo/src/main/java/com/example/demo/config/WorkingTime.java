package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class WorkingTime {

    private static final long START_TIME = System.currentTimeMillis();

    public long time() {
        return System.currentTimeMillis() - START_TIME;
    }
}
