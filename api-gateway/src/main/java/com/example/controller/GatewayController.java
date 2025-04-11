package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/cars")
public class GatewayController {
    private final WebClient webClient = WebClient.create("http://localhost:8081");

    @GetMapping
    public String getAllBooks() {
        return webClient.get().uri("/cars").retrieve().bodyToMono(String.class).block();
    }
}
