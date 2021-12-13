package com.example.payroll.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer levelStack;

}
