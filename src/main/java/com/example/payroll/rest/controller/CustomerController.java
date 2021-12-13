package com.example.payroll.rest.controller;

import com.example.payroll.rest.dto.CustomerDto;
import com.example.payroll.services.CustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomersService customersService;

    @GetMapping("/customers")
    public CollectionModel<EntityModel<CustomerDto>> getAllCustomers() {
        List<EntityModel<CustomerDto>> customers = customersService.getAllEntityModelCustomers();
        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
    }


    @GetMapping("/customers/{id}")
    public EntityModel<CustomerDto> getOneCustomerById(@PathVariable Long id) {
        return customersService.getCustomerDto(id);

    }

    @PostMapping("/customers")
    public ResponseEntity<?> createNewCustomer(@RequestBody CustomerDto newCustomer) {
        EntityModel<CustomerDto> entityModel = customersService.createCustomerDtoEntityModel(newCustomer);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        customersService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/customers/{id}")
    public ResponseEntity<?> replaceCustomer(@RequestBody CustomerDto newCustomer, @PathVariable Long id) {
        EntityModel<CustomerDto> entityModel = customersService.updateCustomerAndGetModel(newCustomer, id);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);


    }

    @GetMapping("/customers/filter")
    public CollectionModel<EntityModel<CustomerDto>> getCustomersByFilters(@RequestParam String name) {
        List<EntityModel<CustomerDto>> customers = customersService.getAllEntityModelCustomersByFilter(name);
        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());

    }


}
