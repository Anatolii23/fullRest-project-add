package com.example.payroll.until;

import com.example.payroll.rest.controller.EmployeeController;
import com.example.payroll.rest.dto.EmployeeDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeeDto, EntityModel<EmployeeDto>> {

    @Override
    public EntityModel<EmployeeDto> toModel(EmployeeDto employee) {

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getOneEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployee()).withRel("employees"));
    }
}
