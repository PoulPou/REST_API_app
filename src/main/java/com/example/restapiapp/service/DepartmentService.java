package com.example.restapiapp.service;

import com.example.restapiapp.exception.departmentException.DeletingNonEmptyDepartmentException;
import com.example.restapiapp.exception.departmentException.NonExistingDepartmentException;
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
    public void deleteDepartment(Long id) throws DeletingNonEmptyDepartmentException {
        if (pr.countByDepartment_Id(id) == 0) {
            dr.deleteById(id);
        } else {
            throw new DeletingNonEmptyDepartmentException();
        }
    }

    //    получение списка всех главенствующих департаментов для департамента Id
    public List<Department> headDepartment(long id) throws NonExistingDepartmentException {
        List<Department> departmentsList = new ArrayList<>();
        long idForCycle = id;
//        добавляю в departmentList главный департамент для департамента idForCycle
        while (dr.findById(idForCycle).orElseThrow(NonExistingDepartmentException :: new).getHeadDepartmentId() != null) {
            Department department = dr.findById(idForCycle).get().getHeadDepartmentId();
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
    public List<Department> getSubordinateDepartment(Long id) throws NonExistingDepartmentException {
        Department d = dr.findById(id).orElseThrow(NonExistingDepartmentException :: new);
        return dr.findAllByHeadDepartmentId_Id(id);
    }

    //  поиск департаментов по Id
    public List<Object> getDepartmentById(Long id) throws NonExistingDepartmentException {
        Department d = dr.findById(id).orElseThrow(NonExistingDepartmentException :: new);
        return getDepartmentsByName(d.getName());
    }

    //  перевод всех сотрудников в другой департамент
    public void transferPersonsToAnotherDepartment(Long departmentNew, Long departmentOld) throws NonExistingDepartmentException {
        //руководитель расформированного департамента перестает быть руководителем
        Department d = dr.findById(departmentOld).orElseThrow(NonExistingDepartmentException :: new);
        Department newDepartment = dr.findById(departmentNew).orElseThrow(NonExistingDepartmentException :: new);
        Person oldSupervisor = pr.findPersonBySupervisorAndDepartment_Name(true, d.getName());
        oldSupervisor.setSupervisor(false);
        List<Person> personsOldDepartment = pr.findAllByDepartment_Id(departmentOld);
        for (Person p : personsOldDepartment) {
            p.setDepartment(newDepartment);
            pr.save(p);
        }
    }

    //  получение всех сотрудников департамента
    public List<Person> getPersonsByDepartment(Long departmentId) throws NonExistingDepartmentException {
        Department department = dr.findById(departmentId).orElseThrow(NonExistingDepartmentException :: new);
        return pr.findAllByDepartment_Id(departmentId);
    }

    //  заработный фонд департамента
    public long salaryFoundByDepartment(Long departmentId) throws NonExistingDepartmentException {
        Department department = dr.findById(departmentId).orElseThrow(NonExistingDepartmentException :: new);
        return pr.salaryFoundByDepartment(departmentId);
    }

    //  кто в подчинении у данного депортамента
    public List<Department> getAllDownDepartmentStart(Long id) throws NonExistingDepartmentException{
        Department department = dr.findById(id).orElseThrow(NonExistingDepartmentException :: new);
        List<Department> departmentList = new ArrayList<>();
        getAllDownDepartment(id, departmentList);
        return departmentList;
    }
    //  рекурсивный метод по поиску подчиненных департаментов
    public void getAllDownDepartment(Long id, List<Department> departmentList) {
        if(dr.countByHeadDepartmentId_Id(id) == 0)
            return;

        List<Department> departments = dr.findAllByHeadDepartmentId_Id(id);
        for (Department dep : departments){
            departmentList.add(dep);

            getAllDownDepartment(dep.getId(), departmentList);
        }
    }

}
