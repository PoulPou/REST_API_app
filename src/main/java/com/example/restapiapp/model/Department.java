package com.example.restapiapp.model;

import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.Id;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;  //наименование

    @NotEmpty(message = "Date of creation should not be empty")
    @PastOrPresent(message = "Date of creation should be valid")
    private Date dateOfCreation;  //дата создания

    private Department headDepartmentId;

    public Department(int id, String name, Date dateOfCreation) {
        this.id = id;
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.headDepartmentId = null;
    }
}
