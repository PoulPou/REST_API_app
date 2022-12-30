package com.example.restapiapp.controller;

import com.example.restapiapp.exception.personException.NullPersonException;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.PersonRepository;
//import com.example.restapiapp.repository.PersonRepository;
//import com.example.restapiapp.service.DepartmentAndPersonService;
import com.example.restapiapp.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPerson(){
        return personService.findAllPerson();
    }

    @PostMapping
    public void createPerson(@RequestBody Person person) throws Exception {
        personService.createPerson(person);
    }

    @PutMapping
    public void updatePerson(@RequestBody Person person) throws Exception {
        personService.updatePerson(person);
    }

    @DeleteMapping
    public void deletePerson(@RequestParam Long id, Date date) throws Exception {
        personService.deletePerson(id, date);
    }

    @GetMapping("/{id}")
    public Person getInfo(@PathVariable Long id) throws NullPersonException {
        return personService.getById(id);
    }

    @GetMapping("/{id}/boss")
    public Person getBossThisPerson(@PathVariable Long id) throws NullPersonException {
        return personService.getBossThisPerson(id);
    }

    @PutMapping("/{id}/transfer")
    public void transferPerson(@PathVariable Long id, @RequestParam Long idDepartment) throws Exception {
        personService.transferPerson(id, idDepartment);
    }
}
