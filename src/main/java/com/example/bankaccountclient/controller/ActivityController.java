package com.example.bankaccountclient.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ActivityController {

    private final RestTemplate restTemplate;

    public ActivityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/activity")
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public String getRandomActivity() {
        String response = restTemplate.getForObject("http://localhost:8086/listUser", String.class);
        return response;
    }

    public String fallbackRandomActivity(Throwable throwable) {
        return "Sorry for that";
    }


}