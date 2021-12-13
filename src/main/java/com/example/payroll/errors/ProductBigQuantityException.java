package com.example.payroll.errors;

public class ProductBigQuantityException extends RuntimeException{
    public ProductBigQuantityException(Integer quantity) {
        super("incorrect quantity " + quantity);
    }
}
