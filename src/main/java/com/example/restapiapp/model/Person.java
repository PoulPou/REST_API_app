package com.example.restapiapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "person_db")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length=30, nullable=false)
    private String name; //имя

    @Column(length=30, nullable=false)
    private String surname;  //фамилия

    @Column(length=30)
    private String patronymic;  //отчество (необязательно)

    @Column(nullable=false)
    private boolean sex;  //пол

    @Column(nullable=false)
    private Date birthday;  //день рождения

    @Column(nullable=false)
    private String phoneNumber;  //номер телефона

    @Column(nullable=false)
    @Email
    private String mail;  //e-mail

    @Column(nullable=false)
    @PastOrPresent
    private Date dateOfEmployment;  //дата трудоустройства

    @PastOrPresent
    private Date dateOfDismissal;  //дата увольнения (необязательно)

//    private int post;  //должность (выбирается из справочника)

    @Column(nullable=false)
    @Positive
    private double salary;  //оклад в руб.

    @Column(nullable=false)
    private boolean supervisor;  //руководитель ли он?

    @ManyToOne
    private Department department;

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }
}
