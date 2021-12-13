package com.example.payroll.errors;

public class ProductNotFoundException  extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("product doesn`t exist with id : " + id);
    }
}
