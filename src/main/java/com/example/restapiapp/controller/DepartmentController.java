package com.example.restapiapp.controller;

import com.example.restapiapp.exception.departmentException.DeletingNonEmptyDepartmentException;
import com.example.restapiapp.exception.departmentException.NonExistingDepartmentException;
import com.example.restapiapp.model.Department;
import com.example.restapiapp.model.Person;
import com.example.restapiapp.service.DepartmentService;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> findAllDepartment(){
        return departmentService.findAllDepartment();
    }

    @PostMapping
    public void createDepartment(@RequestBody Department department){
        departmentService.createDepartment(department);
    }

    @DeleteMapping
    public void deleteDepartment (@RequestParam Long id) throws DeletingNonEmptyDepartmentException {
            departmentService.deleteDepartment(id);
    }

    @GetMapping("/{id}/head")
    public List<Department> findAllHeadDepartment(@PathVariable Long id) throws NonExistingDepartmentException {
        return departmentService.headDepartment(id);

    }

    @GetMapping("/search")
    public List<Object> getDepartmentsByName(@RequestParam String name){
        return departmentService.getDepartmentsByName(name);
    }

    @GetMapping("/{id}/subordinate")
    public List<Department> getSubordinateDepartment(@PathVariable Long id) throws NonExistingDepartmentException {
        return departmentService.getSubordinateDepartment(id);
    }

    @GetMapping("/{oldDepartment}/transfer")
    public void transferPersonsToAnotherDepartment(@RequestParam Long newDepartment,
                                                   @PathVariable Long oldDepartment) throws NonExistingDepartmentException {
        departmentService.transferPersonsToAnotherDepartment(newDepartment, oldDepartment);
    }

    @GetMapping("/{id}/persons")
    public List<Person> getAllPersonsByDepartment(@PathVariable Long id) throws NonExistingDepartmentException {
        return departmentService.getPersonsByDepartment(id);
    }

    @GetMapping("/{id}/found")
    public long salaryFoundByDepartment(@PathVariable Long id) throws NonExistingDepartmentException {
        return departmentService.salaryFoundByDepartment(id);
    }

    @GetMapping("/{id}/downDepartment")
    public List<Department> getDownDepartmentById(@PathVariable Long id) throws NonExistingDepartmentException{
        return departmentService.getAllDownDepartmentStart(id);
    }

    @GetMapping("/{id}")
    public List<Object> getDepartmentById(@PathVariable Long id) throws NonExistingDepartmentException {
        return departmentService.getDepartmentById(id);
    }

}
