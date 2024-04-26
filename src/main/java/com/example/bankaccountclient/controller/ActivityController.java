package com.example.bankaccountclient.controller;

import com.example.bankaccountclient.model.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<String> getRandomActivity() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/listUser", String.class);
        String responseBody = responseEntity.getBody();
        log.info("Activity received: " + responseBody);
        return ResponseEntity.ok(responseBody);
    }
    public String fallbackRandomActivity(Throwable throwable) {
        return "Watch a video from TechPrimers";
    }

}