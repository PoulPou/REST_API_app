package com.example.restapiapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.*;

@EnableScheduling
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DepartmentsFound {

    @Id
    @GeneratedValue
    private Long id;

    private Long departmentId; //id департамента

    private long found; //фонд департамента

    public DepartmentsFound(Long departmentId, long found) {
        this.departmentId = departmentId;
        this.found = found;
    }

}
