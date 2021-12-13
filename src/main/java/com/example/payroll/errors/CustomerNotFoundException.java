package com.example.payroll.errors;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("not found customer with id : " + id);
    }
}
