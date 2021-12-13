package com.example.payroll.services;

import com.example.payroll.entity.*;
import com.example.payroll.errors.*;
import com.example.payroll.repository.CustomerRepository;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.OrderRepository;
import com.example.payroll.repository.ProductRepository;
import com.example.payroll.rest.dto.OrderDto;
import com.example.payroll.until.EntityDtoMapper;
import com.example.payroll.until.OrderModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServices {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final OrderModelAssembler assembler;

    public OrderDto createNewOrder(OrderDto orderDto, Long customerId, Long employeeId) {
        Order order = EntityDtoMapper.mappedToOrderEntity(orderDto);
        order.setStatus(Order.Status.IN_PROGRESS);
        order.setDate(new Date());
        order.setPrice(new BigDecimal("0"));
        order.setProduct(checkProductAndSet(order));
        Customer customerRepositoryById = getCustomerFromRepositoryById(customerId);
        order.setCustomer(customerRepositoryById);
        Employee employeeRepositoryById = getEmployeeFromRepositoryById(employeeId);
        order.setEmployee(employeeRepositoryById);
        List<Order> customerOrdersSet = customerRepositoryById.getCustomerOrdersList();
        customerOrdersSet.add(order);
        List<Order> employeeOrdersSet = employeeRepositoryById.getEmployeeOrdersList();
        employeeOrdersSet.add(order);
        orderRepository.save(order);
        customerRepository.save(customerRepositoryById);
        employeeRepository.save(employeeRepositoryById);
        return EntityDtoMapper.mappedToOrderDto(order);
    }

    private List<OrderedProduct> checkProductAndSet(Order order) {
        List<OrderedProduct> productList = new ArrayList<>();
        for (OrderedProduct product : order.getProduct()) {
            Product productFromRepoByName = (productRepository.findByName(product.getProduct().getName())
                    .orElseThrow(() -> new ProductNotFoundException(product.getId())));
            if (product.getQuantity() <= productFromRepoByName.getLevelStack()) {
                productFromRepoByName.setLevelStack((productFromRepoByName.getLevelStack() - product.getQuantity()));
                BigDecimal orderPrice = order.getPrice().add(BigDecimal.valueOf(product.getQuantity()).multiply(productFromRepoByName.getPrice()));
                product.getProduct().setPrice(productFromRepoByName.getPrice());
                product.getProduct().setId(productFromRepoByName.getId());
                productList.add(product);
                order.setPrice(orderPrice);
                productRepository.save(productFromRepoByName);
            } else {
                throw new ProductBigQuantityException(product.getQuantity());
            }
        }
        return productList;
    }

    private Employee getEmployeeFromRepositoryById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    private Customer getCustomerFromRepositoryById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    public List<EntityModel<OrderDto>> getAllEntityModelsOrders() {
        return orderRepository.findAll().stream()
                .map(EntityDtoMapper::mappedToOrderDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    public EntityModel<OrderDto> getOrderDto(Long id) {
        OrderDto orderDto = orderRepository.findById(id).map(EntityDtoMapper::mappedToOrderDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(orderDto);
    }

    public ResponseEntity<?> getResponseEntityForDeleteMapping(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.CANCELLED);
            orderRepository.save(order);
            return ResponseEntity.ok(assembler.toModel(EntityDtoMapper.mappedToOrderDto(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    public ResponseEntity<?> getResponseEntityForPutMappingOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.COMPLETED);
            orderRepository.save(order);
            return ResponseEntity.ok(assembler.toModel(EntityDtoMapper.mappedToOrderDto(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }
    public List<EntityModel<OrderDto>> getAllEntityModelsOrdersByFilters(String name, Double minPrice, Double maxPrice) {
        List<Order> byNameAndStatus;
        if (name != null) {
            byNameAndStatus = orderRepository.findByNameAndStatus(name);
        } else {
            byNameAndStatus = orderRepository.findAll();
        }
        return byNameAndStatus.stream()
                .filter(order -> minPrice == null || minPrice <= order.getPrice().doubleValue())
                .filter(order -> maxPrice == null || maxPrice >= order.getPrice().doubleValue())
                .map(EntityDtoMapper::mappedToOrderDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }
}
