package com.example.restapiapp.controller;

import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public List<Department> findAllDepartment(){
        return departmentService.findAllDepartment();
    }

    @PostMapping("/department")
    public void createDepartment(@RequestBody Department department){
        departmentService.createDepartment(department);
    }

    @DeleteMapping("/department")
    public String deleteDepartment (@RequestParam Long id){
        return departmentService.deleteDepartment(id);
    }

    @GetMapping("/head_dep")
    public List<Department> findAllHeadDepartment(@RequestParam long id){
        return departmentService.headDepartment(id);
    }

    @GetMapping("/search_dep")
    public List<Object> getDepartmentsByName(@RequestParam String name){
        return departmentService.getDepartmentsByName(name);
    }

    @GetMapping("/subordinate_dep")
    public List<Department> getSubordinateDepartment(@RequestParam Long id){
        return departmentService.getSubordinateDepartment(id);
    }

    @GetMapping("/transfer_dep")
    public void transferPersonsToAnotherDepartment(@RequestParam Long newDepartment,
                                                   @RequestParam Long oldDepartment){
        departmentService.transferPersonsToAnotherDepartment(newDepartment, oldDepartment);
    }

    @GetMapping("/personByDep")
    public List<Person> getAllPersonsByDepartment(@RequestParam Long departmentId){
        return departmentService.getPersonsByDepartment(departmentId);
    }
}
