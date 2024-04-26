package com.example.bankaccountclient.controller;

import com.example.bankaccountclient.model.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/activity")
@RestController
public class ActivityController {

    private RestTemplate restTemplate;

    public ActivityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public String getRandomActivity() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/listUser", String.class);
            return response.getBody(); // Возвращаем только тело ответа
        } catch (Exception e) {
            log.error("Failed to fetch activity: {}", e.getMessage());
            return fallbackRandomActivity(e); // Вызываем метод отката
        }
    }

    public String fallbackRandomActivity(Throwable throwable) {
        return "Sorry for that";
    }


}