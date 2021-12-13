package com.example.payroll.errors;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("employee doesnt exist with id : " + id);
    }
}
