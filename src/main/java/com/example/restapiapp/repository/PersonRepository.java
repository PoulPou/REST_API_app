package com.example.restapiapp.repository;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    int countByDepartment_Id(Long id);

    int countPersonByDepartment(Department d);

    Person findPersonBySupervisorAndDepartment_Name(boolean b, String name);

    int countByDepartment_Name(String name);

    List<Person> findAllByDepartment_Id(Long id);

    @Query(value = "SELECT SUM(salary) FROM person WHERE person.department_id = :departmentId", nativeQuery = true)
    long salaryFoundByDepartment(Long departmentId);

    @Query(value = "SELECT MAX(dateOfEmployment) FROM person WHERE person.department_id = :id", nativeQuery = true)
    Person mostSeniorEmployee(Long id);
}

