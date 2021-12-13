package com.example.payroll.until;

import com.example.payroll.entity.*;
import com.example.payroll.rest.dto.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDtoMapper {
    public static ProductDto mappedToProductDto(Product product) {
        ProductDto productDto = ProductDto.builder().build();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static OrderedProductDto mappedToOrderedProductDto(OrderedProduct product) {
        OrderedProductDto productDto = OrderedProductDto.builder().build();
        BeanUtils.copyProperties(product, productDto);
        productDto.setProductDto(EntityDtoMapper.mappedToProductDto(product.getProduct()));
        return productDto;
    }

    public static Product mappedToProductEntity(ProductDto productDto) {
        Product product = Product.builder().build();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

    public static OrderedProduct mappedToOrderedProductEntity(OrderedProductDto productDto) {
        OrderedProduct product =  OrderedProduct.builder().build();
        BeanUtils.copyProperties(productDto, product);
        product.setProduct(EntityDtoMapper.mappedToProductEntity(productDto.getProductDto()));
        return product;
    }

    public static EmployeeDto mappedToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = EmployeeDto.builder().build();
        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }

    public static Employee mappedToEmployeeEntity(EmployeeDto employeeDto) {
        Employee employee = Employee.builder().build();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }

    public static CustomerDto mappedToCustomerDto(Customer customer) {
        CustomerDto customerDto = CustomerDto.builder().build();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

    public static Customer mappedToCustomerEntity(CustomerDto customerDto) {
        Customer customer = Customer.builder().build();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    public static OrderDto mappedToOrderDto(Order order) {
        OrderDto orderDto = OrderDto.builder().build();
        BeanUtils.copyProperties(order, orderDto);
        List<OrderedProductDto> productDtoList = order.getProduct()
                .stream()
                .map(EntityDtoMapper::mappedToOrderedProductDto)
                .collect(Collectors.toList());
        orderDto.setProduct(productDtoList);
        return orderDto;
    }

    public static Order mappedToOrderEntity(OrderDto orderDto) {
        Order order = Order.builder().build();
        BeanUtils.copyProperties(orderDto, order);
        List<OrderedProduct> productList = orderDto.getProduct()
                .stream()
                .map(EntityDtoMapper::mappedToOrderedProductEntity)
                .collect(Collectors.toList());
        order.setProduct(productList);
        return order;
    }
}
