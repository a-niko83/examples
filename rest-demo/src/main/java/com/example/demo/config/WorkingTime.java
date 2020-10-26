package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class WorkingTime {
    /**
     * Время запуска приложения в мс.
     */
    private static final long START_TIMEOUT = 60000;
    private static final long START_TIME = System.currentTimeMillis();

    public long time() {
        return System.currentTimeMillis() - START_TIME;
    }

    /**
     * Проверяет статус запуска приложения.
     * @return true - приложение запущено
     */
    public boolean isStarted() {
         return time() > 60000;
    }
}
