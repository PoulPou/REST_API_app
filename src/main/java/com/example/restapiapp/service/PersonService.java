package com.example.restapiapp.service;

import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository pr;
    private final DepartmentRepository dr;

    public PersonService(PersonRepository pr, DepartmentRepository dr) {
        this.pr = pr;
        this.dr = dr;
    }

    //  сздание персоны
    public void createPerson(Person person){
        pr.save(person);
    }

    // все персоны
    public List<Person> findAllPerson(){
        return pr.findAll();
    }




}
