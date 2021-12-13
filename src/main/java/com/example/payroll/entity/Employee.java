package com.example.payroll.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "employees")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private Double salary;
    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL)
    @Builder.Default
    private List<Order> employeeOrdersList = new ArrayList<>();

}
