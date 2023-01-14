package com.example.restapiapp.service;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.DepartmentsFound;
import com.example.restapiapp.repository.DepartmentFoundRepository;
import com.example.restapiapp.repository.DepartmentRepository;
import com.example.restapiapp.repository.PersonRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentsFoundService {


    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final DepartmentFoundRepository departmentFoundRepository;

    public DepartmentsFoundService(DepartmentRepository departmentRepository, PersonRepository personRepository, DepartmentFoundRepository departmentFoundRepository) {
        this.departmentRepository = departmentRepository;
        this.personRepository = personRepository;
        this.departmentFoundRepository = departmentFoundRepository;
    }

    public void deleteDepartmentToMap(Long id){
        departmentFoundRepository.deleteAllByDepartmentId(id);
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void saveFound(){
        List<Department> departmentList = departmentRepository.findAll();
        if(departmentList.size() > 0) {
            for (Department department : departmentList){
                if (personRepository.countPersonByDepartment(department)>0){
                long found = personRepository.salaryFoundByDepartment(department.getId());
                departmentFoundRepository.save(new DepartmentsFound(department.getId(), found));
                }else {
                    departmentFoundRepository.save(new DepartmentsFound(department.getId(), 0));
                }
            }
        }
    }

}
