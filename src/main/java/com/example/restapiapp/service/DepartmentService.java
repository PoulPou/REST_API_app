package com.example.restapiapp.service;

import com.example.restapiapp.exception.departmentException.DeletingNonEmptyDepartmentException;
import com.example.restapiapp.exception.departmentException.NullDepartmentException;
import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentsFoundService departmentsFoundService;

    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;

    public DepartmentService(DepartmentRepository departmentRepository, PersonRepository personRepository, DepartmentsFoundService departmentsFoundService) {
        this.departmentRepository = departmentRepository;
        this.personRepository = personRepository;
        this.departmentsFoundService = departmentsFoundService;

    }

    // показать все департаменты
    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }

    // создание департамента
    public void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    //  удаление департамента
    @Transactional
    public void deleteDepartment(Long id) throws DeletingNonEmptyDepartmentException, NullDepartmentException {
        Department d = departmentRepository.findById(id).orElseThrow(NullDepartmentException:: new);
        if (personRepository.countByDepartment_Id(id) == 0) {
            departmentRepository.deleteById(id);
            departmentsFoundService.deleteDepartmentToMap(id);
        } else {
            throw new DeletingNonEmptyDepartmentException();
        }
    }

    //    получение списка всех главенствующих департаментов для департамента Id
    public List<Department> headDepartment(long id) throws NullDepartmentException {
        List<Department> departmentsList = new ArrayList<>();
        long idForCycle = id;
//        добавляю в departmentList главный департамент для департамента idForCycle
        while (departmentRepository.findById(idForCycle).orElseThrow(NullDepartmentException:: new).getHeadDepartmentId() != null) {
            Department department = departmentRepository.findById(idForCycle).get().getHeadDepartmentId();
            departmentsList.add(department);
//        Изменяю idForCycle на Id полученного (главного) департамента
            idForCycle = department.getId();
        }
        return departmentsList;
    }

    //    поиск департаментов по наименованию
    public List<Object> getDepartmentsByName(String name) {
        List<Object> list = new ArrayList<>();
        list.add(departmentRepository.findDepartmentByName(name));
        list.add(personRepository.findPersonBySupervisorAndDepartment_Name(true, name));
        list.add(personRepository.countByDepartment_Name(name));

        return list;
    }

    //  прямые подчиненные департаменты данного департамента
    public List<Department> getSubordinateDepartment(Long id) throws NullDepartmentException {
        Department d = departmentRepository.findById(id).orElseThrow(NullDepartmentException:: new);
        return departmentRepository.findAllByHeadDepartmentId_Id(id);
    }

    //  поиск департаментов по Id
    public List<Object> getDepartmentById(Long id) throws NullDepartmentException {
        Department d = departmentRepository.findById(id).orElseThrow(NullDepartmentException:: new);
        return getDepartmentsByName(d.getName());
    }

    //  перевод всех сотрудников в другой департамент
    public void transferPersonsToAnotherDepartment(Long departmentNew, Long departmentOld) throws NullDepartmentException {
        //руководитель расформированного департамента перестает быть руководителем
        Department d = departmentRepository.findById(departmentOld).orElseThrow(NullDepartmentException:: new);
        Department newDepartment = departmentRepository.findById(departmentNew).orElseThrow(NullDepartmentException:: new);
        Person oldSupervisor = personRepository.findPersonBySupervisorAndDepartment_Name(true, d.getName());
        oldSupervisor.setSupervisor(false);
        List<Person> personsOldDepartment = personRepository.findAllByDepartment_Id(departmentOld);
        for (Person p : personsOldDepartment) {
            p.setDepartment(newDepartment);
            personRepository.save(p);
        }
    }

    //  получение всех сотрудников департамента
    public List<Person> getPersonsByDepartment(Long departmentId) throws NullDepartmentException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(NullDepartmentException:: new);
        return personRepository.findAllByDepartment_Id(departmentId);
    }

    //  заработный фонд департамента
    public long salaryFoundByDepartment(Long departmentId) throws NullDepartmentException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(NullDepartmentException:: new);
        return personRepository.salaryFoundByDepartment(departmentId);
    }

    //  кто в подчинении у данного депортамента
    public List<Department> getAllDownDepartmentStart(Long id) throws NullDepartmentException {
        Department department = departmentRepository.findById(id).orElseThrow(NullDepartmentException:: new);
        List<Department> departmentList = new ArrayList<>();
        getAllDownDepartment(id, departmentList);
        return departmentList;
    }
    //  рекурсивный метод по поиску подчиненных департаментов
    public void getAllDownDepartment(Long id, List<Department> departmentList) {
        if (departmentRepository.countByHeadDepartmentId_Id(id) == 0)
            return;

        List<Department> departments = departmentRepository.findAllByHeadDepartmentId_Id(id);
        for (Department dep : departments) {
            departmentList.add(dep);

            getAllDownDepartment(dep.getId(), departmentList);
        }
    }
}
