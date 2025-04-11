package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {

    @GetMapping("/")
    public String coreServiceHome() {
        return "Core Service is running!";
    }

    @GetMapping("/test")
    public String helloEndpoint() {
        return "test response";
    }
}
