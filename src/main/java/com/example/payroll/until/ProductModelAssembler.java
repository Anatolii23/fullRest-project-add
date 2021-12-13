package com.example.payroll.until;

import com.example.payroll.rest.controller.CustomerController;
import com.example.payroll.rest.controller.ProductController;
import com.example.payroll.rest.dto.ProductDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {
    @Override
    public EntityModel<ProductDto> toModel(ProductDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ProductController.class).getOneProductById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }
}
