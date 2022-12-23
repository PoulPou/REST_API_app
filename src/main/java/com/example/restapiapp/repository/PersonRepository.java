package com.example.restapiapp.repository;

import com.example.restapiapp.model.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    //сколько сотрудников в департаменте
    @Query("SELECT COUNT(*) FROM person WHERE department = :department")
    int getCountPersonInDepartment (long department);

    //начальник департамента
    @Query("SELECT * FROM person WHERE department = :department AND supervisor = true")
    Person getSupervisorOfDepartment(long department);

    //сумма зарплат работников департамента id
    @Query("SELECT SUM(salary) FROM person WHERE department = :department")
    double salaryFoundDepartment (long id);

    //все работники департамента id
    @Query("SELECT * FROM person WHERE department = :department")
    List<Person> allPersonOfDepartment (long id);
}
