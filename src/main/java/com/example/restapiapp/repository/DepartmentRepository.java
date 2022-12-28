package com.example.restapiapp.repository;

import com.example.restapiapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

    Department findDepartmentByName(String name);

    List<Department> findAllByHeadDepartmentId_Id(Long id);

    Department findDepartmentByHeadDepartmentId_Id(Long id);

    Long countByHeadDepartmentId_Id(Long id);

    List<Department> findDepartmentsByHeadDepartmentId(Long id);
}
