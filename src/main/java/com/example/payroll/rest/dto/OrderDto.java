package com.example.payroll.rest.dto;

import com.example.payroll.entity.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @NotBlank
    private String description;
    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Order.Status status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal price;
    @NotNull
    private List<OrderedProductDto> product;
}
