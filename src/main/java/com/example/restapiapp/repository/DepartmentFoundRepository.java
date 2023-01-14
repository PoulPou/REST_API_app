package com.example.restapiapp.repository;

import com.example.restapiapp.model.DepartmentsFound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentFoundRepository extends JpaRepository<DepartmentsFound, Long> {

    void deleteAllByDepartmentId(Long id);

}
