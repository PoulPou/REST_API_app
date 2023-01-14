package com.example.restapiapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true, length=100, nullable=false)
    private String name;  //наименование

    @Column(nullable=false)
    private Date dateOfCreation;  //дата создания

    @OneToOne
    private Department headDepartmentId; //id главного департамента

    public Department(String name, Date dateOfCreation, Department headDepartmentId) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.headDepartmentId = headDepartmentId;
    }
}
