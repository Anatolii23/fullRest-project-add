package com.example.payroll.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @DecimalMin("0")
    private BigDecimal price;
    @NotNull
    @Min(0)
    private Integer levelStack;
}
