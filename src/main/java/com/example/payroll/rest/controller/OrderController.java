package com.example.payroll.rest.controller;

import com.example.payroll.rest.dto.OrderDto;
import com.example.payroll.services.OrderServices;
import com.example.payroll.until.OrderModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderModelAssembler assembler;
    private final OrderServices orderServices;


    @GetMapping("/orders")
    public CollectionModel<EntityModel<OrderDto>> getAllOrders() {
        List<EntityModel<OrderDto>> orders = orderServices.getAllEntityModelsOrders();
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }


    @GetMapping("/orders/{id}")
    public EntityModel<OrderDto> getOneOrder(@PathVariable Long id) {
        return orderServices.getOrderDto(id);
    }

    @PostMapping("/orders")
    public ResponseEntity<EntityModel<OrderDto>> newOrder(@Valid @RequestBody OrderDto orderDto,
                                                          @Valid @RequestParam Long customerId,
                                                          @Valid @RequestParam Long employeeId) {
        OrderDto newOrder = orderServices.createNewOrder(orderDto, customerId, employeeId);
        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOneOrder(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }


    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        return orderServices.getResponseEntityForDeleteMapping(id);
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {
        return orderServices.getResponseEntityForPutMappingOrder(id);
    }
    @GetMapping("/orders/filter")
    public CollectionModel<EntityModel<OrderDto>> getAllOrdersByFilter(@RequestParam(name = "name",required = false) String name,
                                                                       @RequestParam(name = "minPrice",required = false) Double minPrice,
                                                                       @RequestParam(name = "maxPrice",required = false) Double maxPrice) {
        List<EntityModel<OrderDto>> orders = orderServices.getAllEntityModelsOrdersByFilters(name,minPrice,maxPrice);
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }

}
