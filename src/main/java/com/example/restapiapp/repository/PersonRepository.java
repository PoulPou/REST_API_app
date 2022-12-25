package com.example.restapiapp.repository;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    int countByDepartment_Id(Long id);

    Person findPersonBySupervisorAndDepartment_Name(boolean b, String name);

    int countByDepartment_Name(String name);

    List<Person> findAllByDepartment_Id(Long id);


}
