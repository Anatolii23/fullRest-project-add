package com.example.payroll.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ordered_products")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderedProduct {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    private Product product;
    private Integer quantity;
}
