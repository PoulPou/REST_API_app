package com.example.restapiapp.service;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentAndPersonService {

    private DepartmentRepository departmentRepository;
    private PersonRepository personRepository;


    public DepartmentAndPersonService(DepartmentRepository departmentRepository, PersonRepository personRepository) {
        this.departmentRepository = departmentRepository;
        this.personRepository = personRepository;
    }


//  удаление департамента
    public String deleteDepartment(long id) {
        if (personRepository.getCountPersonInDepartment(id) != 0) {
            departmentRepository.deleteDepartmentById(id);
            return "Department delete";
        } else {
            return "Department has employees. Department delete impossible.";
        }
    }

//    получение списка главенствующих департаментов для департамента Id
    public List<Department> headDepartment(long id){
        List<Department> departmentsList = new ArrayList<>();
        long idForCycle = id;
//        добавляю в departmentList главный департамент для департамента idForCycle
        if (departmentRepository.getIdHeadDepartment(idForCycle) != idForCycle){
           Department department = departmentRepository.getDepartmentById(departmentRepository.getIdHeadDepartment(idForCycle));
           departmentsList.add(department);
//           Изменяю idForCycle на Id главного департамента для полученного департамента(department)
           idForCycle = departmentRepository.getIdHeadDepartment(department.getId());
        }
        return departmentsList;
    }

//    поиск департаментов по наименованию
    public Department getDepartmentsByName (String name){
        return departmentRepository.getDepartmentByName(name);
    }

//    зарплатный фонд департамента Id
    public double salaryFoundDepartment (long id){
        return personRepository.salaryFoundDepartment(id);
    }

//    все сотрудники департамента Id
    public List<Person> allPersonOfDepartment(long id){
        return personRepository.allPersonOfDepartment(id);
    }


}
