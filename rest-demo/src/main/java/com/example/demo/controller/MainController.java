package com.example.demo.controller;

import com.example.demo.config.WorkingTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final WorkingTime workingTime;

    @GetMapping("/log/{value}")
    public ResponseEntity calculateLog(@PathVariable("value") double value) {
        log.info("Calculate log({})", value);
        if (!workingTime.isStarted()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Math.log(value));
    }
}
