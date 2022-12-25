package com.example.restapiapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
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
}
