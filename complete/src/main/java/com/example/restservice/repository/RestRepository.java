package com.example.restservice.repository;

import com.example.restservice.GreetingEntity;
import org.springframework.stereotype.Component;

@Component
public class RestRepository {

    public GreetingEntity findGreeting(Long id) {
        return new GreetingEntity();
    }
}
