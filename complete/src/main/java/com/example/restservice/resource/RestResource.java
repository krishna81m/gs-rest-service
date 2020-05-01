package com.example.restservice.resource;

import com.example.restservice.PersonalGreeting;
import com.example.restservice.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RestResource {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    RestService restService;

    @GetMapping("/greeting")
    public PersonalGreeting greetingByName(@RequestParam(value = "name", defaultValue = "World") String name) {
        PersonalGreeting personalGreeting = restService.getGreeting(name);
        return new PersonalGreeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/greeting/{name}")
    public PersonalGreeting greeting(@PathVariable(name = "name") String name) {
        PersonalGreeting personalGreeting = restService.getGreeting(name);
        return new PersonalGreeting(counter.incrementAndGet(), String.format(template, name));
    }

}
