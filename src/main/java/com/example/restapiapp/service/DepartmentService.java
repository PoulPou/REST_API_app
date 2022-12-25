package com.example.restapiapp.service;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository dr;
    private final PersonRepository pr;

    public DepartmentService(DepartmentRepository dr, PersonRepository pr) {
        this.dr = dr;
        this.pr = pr;
    }

    // показать все департаменты
    public List<Department> findAllDepartment() {
        return dr.findAll();
    }

    // создание департамента
    public void createDepartment(Department department) {
        dr.save(department);
    }

    //  удаление департамента
    public String deleteDepartment(Long id) {
        if (pr.countByDepartment_Id(id) == 0) {
            dr.deleteById(id);
            return "Department delete";
        } else {
            return "Department has employees. Department delete impossible.";
        }
    }

    //    получение списка всех главенствующих департаментов для департамента Id
    public List<Department> headDepartment(long id) {
        List<Department> departmentsList = new ArrayList<>();
        long idForCycle = id;
//        добавляю в departmentList главный департамент для департамента idForCycle
        while (dr.getById(idForCycle).getHeadDepartmentId() != null) {
            Department department = dr.getById(idForCycle).getHeadDepartmentId();
            departmentsList.add(department);
//        Изменяю idForCycle на Id полученного (главного) департамента
            idForCycle = department.getId();
        }
        return departmentsList;
    }

    //    поиск департаментов по наименованию
    public List<Object> getDepartmentsByName(String name) {
        List<Object> list = new ArrayList<>();
        list.add(dr.findDepartmentByName(name));
        list.add(pr.findPersonBySupervisorAndDepartment_Name(true, name));
        list.add(pr.countByDepartment_Name(name));

        return list;
    }

    //  прямые подчиненные департаменты данного департамента
    public List<Department> getSubordinateDepartment(Long id) {
        return dr.findAllByHeadDepartmentId_Id(id);
    }

    //  поиск департаментов по Id
    public Department getDepartmentById(Long id) {
        return dr.getById(id);
    }

    //  перевод всех сотрудников в другой департамент
    public void transferPersonsToAnotherDepartment(Long departmentNew, Long departmentOld) {
        //руководитель расформированного департамента перестает быть руководителем
        Department d = getDepartmentById(departmentOld);
        Person oldSupervisor = pr.findPersonBySupervisorAndDepartment_Name(true, d.getName());
        oldSupervisor.setSupervisor(false);
        List<Person> personsOldDepartment = pr.findAllByDepartment_Id(departmentOld);
        for (Person p : personsOldDepartment) {
            p.setDepartment(getDepartmentById(departmentNew));
            pr.save(p);
        }
    }

    //  получение всех сотрудников департамента
    public List<Person> getPersonsByDepartment(Long departmentId){
        return pr.findAllByDepartment_Id(departmentId);
    }

    //  заработный фонд департамента


}
