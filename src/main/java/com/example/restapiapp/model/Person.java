package com.example.restapiapp.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private long Id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name; //имя

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    private String surname;  //фамилия

    @Size(min = 2, max = 30, message = "Patronymic should be between 2 and 30 characters")
    private String patronymic;  //отчество (необязательно)

    @NotEmpty(message = "Choose a gender")
    @PositiveOrZero
    private double sex;  //пол

    @NotEmpty(message = "Birthday should not be empty")
    @Past(message = "Birthday should not be in the future or present")
    private Date birthday;  //день рождения

    @NotEmpty(message = "Phone number should not be empty")
    @Size(min = 10, max = 12, message = "Phone number is not correct")
    private String phoneNumber;  //номер телефона

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String mail;  //e-mail

    @NotEmpty(message = "Date of employment should not be empty")
    @PastOrPresent(message = "Date of employment should be valid")
    private Date dateOfEmployment;  //дата трудоустройства

    @PastOrPresent(message = "Date of employment should be valid")
    private Date dateOfDismissal;  //дата увольнения (необязательно)

    @NotEmpty(message = "Post should not be empty")
    private int post;  //должность (выбирается из справочника)

    @NotEmpty(message = "Salary should not be empty")
    @Positive(message = "Salary cannot be less than or equal to zero")
    private double salary;  //оклад в руб.

    @NotEmpty(message = "Salary should not be empty")
    @PositiveOrZero
    private double supervisor;  //руководитель ли он?

    private long department;

}
