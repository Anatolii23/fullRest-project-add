package com.example.payroll.until;

import com.example.payroll.rest.controller.OrderController;
import com.example.payroll.entity.Order;
import com.example.payroll.rest.dto.OrderDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> {

    @Override
    public EntityModel<OrderDto> toModel(OrderDto order) {

        EntityModel<OrderDto> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOneOrder(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));

        if (order.getStatus() == Order.Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
