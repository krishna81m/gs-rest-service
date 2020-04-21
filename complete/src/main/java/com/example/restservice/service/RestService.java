package com.example.restservice.service;

import com.example.restservice.GreetingEntity;
import com.example.restservice.repository.RestRepository;
import com.example.restservice.PersonEntity;
import com.example.restservice.repository.GreetingRepository;
import com.example.restservice.repository.PersonRepository;
import com.example.restservice.PersonalGreeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestService {

    @Autowired
    RestRepository restRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GreetingRepository greetingRepository;

    public PersonalGreeting getGreeting(String lastName) {
        createPerson();
        List<PersonEntity> persons = personRepository.findByLastName(lastName);

        createGreeting();
        List<GreetingEntity> greetingEntity = greetingRepository.findByGreeting("hello");

        // restRepository.findGreeting(id);
        PersonalGreeting personalGreeting = mapPersonalGreeting(persons.get(0), greetingEntity.get(0));
        return personalGreeting;
    }

    private void createGreeting() {
        GreetingEntity greetingEntity = new GreetingEntity();
        greetingEntity.setGreeting("hello");

        GreetingEntity save = greetingRepository.save(greetingEntity);
        save.getGreeting().equals("hello");
    }

    private void createPerson() {
        // personRepository.
        PersonEntity person = new PersonEntity();
        person.setFirstName("first");
        person.setLastName("last");
        PersonEntity save = personRepository.save(person);
        save.getFirstName().equals("first");
    }

    private PersonalGreeting mapPersonalGreeting(PersonEntity personEntity, GreetingEntity greetingEntity) {
        return new PersonalGreeting(greetingEntity.getId(),
                "Hello " + personEntity.getFirstName() + " - " + personEntity.getLastName());
    }

}
