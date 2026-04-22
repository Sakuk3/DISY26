package com.example.springboot;

import com.example.common.dto.MessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public MessageDto hello() {
        return new MessageDto("Hello from Spring Boot!");
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
