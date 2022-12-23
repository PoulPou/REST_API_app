package com.example.restapiapp.repository;

import com.example.restapiapp.model.Department;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

//        изменение имени департамента
        @Modifying
        @Query("UPDATE department SET name = :name WHERE id = :id")
        void changeNameDepartment(long id, String name);

//        удаление департамента
        @Modifying
        @Query("DELETE department WHERE id = :id")
        void  deleteDepartmentById(long id);

//        получение id главенствующего департамента департаменты
        @Query("SELECT headDepartment FROM department WHERE id = :id")
        long getIdHeadDepartment (long id);

//        получение департамента по id
        Department getDepartmentById(long id);

//        получение департамента по наименованию
        Department getDepartmentByName(String name);






}
