package com.unla.grupo18.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {


    private Long id;

    @Min(value = 0, message = "current Stock must be greater than or equal to 0")
    private int currentStock;

    @Min(value = 0, message = "critic Stock must be greater than or equal to 0")
    private int criticStock;

    @NotBlank(message = "Product name is mandatory")
    private String productName;
}
