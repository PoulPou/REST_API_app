package com.example.restapiapp.service;

import com.example.restapiapp.enums.Profession;
import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class TestDataService {

    private final DepartmentService departmentService;
    private final PersonService personService;
    private final DepartmentRepository departmentRepository;

    public TestDataService(DepartmentService departmentService, PersonService personService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.personService = personService;
        this.departmentRepository = departmentRepository;
    }

    @PostConstruct
    public void createDataTest() throws Exception {
        createDepartmentData();
        createPersonData();
    }

    private void createDepartmentData() {
        departmentService.createDepartment(new Department("Name1", new Date(), null));
        departmentService.createDepartment(new Department("Name2", new Date(), departmentRepository.findById(1l).get()));
        departmentService.createDepartment(new Department("Name3", new Date(), departmentRepository.findById(2l).get()));
        departmentService.createDepartment(new Department("Name4", new Date(), departmentRepository.findById(3l).get()));
    }

    public void createPersonData() throws Exception {

//        1
        personService.createPerson(new Person("Павел",
                "Мешалкин",
                "Игоревич",
                true,
                new Date(10000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Chief,
                100000.0d,
                true,
                departmentRepository.findById(1l).get()));

//        2
        personService.createPerson(new Person("Иван",
                "Иванов",
                "Иванович",
                true,
                new Date(10000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Senior,
                90000.0d,
                false,
                departmentRepository.findById(1l).get()));

//        3
        personService.createPerson(new Person("Петр",
                "Петров",
                "Петрович",
                true,
                new Date(10000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Middle,
                70000.0d,
                false,
                departmentRepository.findById(1l).get()));

//        4
        personService.createPerson(new Person("Федор",
                "Федоров",
                "Федорович",
                true,
                new Date(100000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Chief,
                150000.0d,
                true,
                departmentRepository.findById(2l).get()));

//        5
        personService.createPerson(new Person("Алексей",
                "Алексеев",
                "Алексеевич",
                true,
                new Date(10000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Senior,
                100000.0d,
                false,
                departmentRepository.findById(2l).get()));

//        6
        personService.createPerson(new Person("Анастасия",
                "Продубнова",
                "Юрьевна",
                false,
                new Date(100000000),
                "9111111111",
                "mail@mail.ru",
                new Date(),
                Profession.Junior,
                30000.0d,
                false,
                departmentRepository.findById(2l).get()));

//        7
        personService.createPerson(new Person("Юрий",
                "Юрьев",
                "Юрьевич",
                true,
                new Date(300000000),
                "9047111111",
                "mail@mail.ru",
                new Date(),
                Profession.Chief,
                200000.0d,
                true,
                departmentRepository.findById(3l).get()));
    }
}
