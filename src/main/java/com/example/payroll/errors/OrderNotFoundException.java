package com.example.payroll.errors;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("order doesn`t exist with id : " + id);
    }
}
