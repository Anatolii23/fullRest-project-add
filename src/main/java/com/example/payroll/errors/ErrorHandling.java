package com.example.payroll.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandling {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String customerNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFoundHandler(OrderNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(ProductBigQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String productBigQuantityHandler(ProductBigQuantityException ex) {
        return ex.getMessage();
    }
}
