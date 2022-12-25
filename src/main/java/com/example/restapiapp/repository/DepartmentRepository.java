package com.example.restapiapp.repository;

import com.example.restapiapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    Department findDepartmentByName(String name);

    List<Department> findAllByHeadDepartmentId_Id(Long id);


}
