package com.example.restapiapp.controller;

import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.PersonRepository;
//import com.example.restapiapp.repository.PersonRepository;
//import com.example.restapiapp.service.DepartmentAndPersonService;
import com.example.restapiapp.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public List<Person> getAllPerson(){
        return personService.findAllPerson();
    }

    @PostMapping("/person")
    public void createPerson(@RequestBody Person person){
        personService.createPerson(person);
    }

//    @PutMapping("/person")
//    public void updatePerson(@RequestBody Person person){
//        personService.save(person);
//    }

}
