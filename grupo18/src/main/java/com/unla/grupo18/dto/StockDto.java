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


    private int currentStock;

    @Min(value = 0, message = "Critic Stock must be greater than or equal to 0")
    private int criticStock;

    private String productName;
}
