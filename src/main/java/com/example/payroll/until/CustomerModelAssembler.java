package com.example.payroll.until;
import com.example.payroll.rest.controller.CustomerController;
import com.example.payroll.rest.dto.CustomerDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerDto, EntityModel<CustomerDto>> {
    @Override
    public EntityModel<CustomerDto> toModel(CustomerDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CustomerController.class).getOneCustomerById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
    }
}
