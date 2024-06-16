package com.unla.grupo18.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoAdd {

    private Long id;

    @NotBlank(message = "Code is mandatory")
    @Size(max = 20, message = "Code must be less than 20 characters")
    private String code;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotNull(message = "Cost Price is mandatory")
    @Min(value = 0, message = "Cost Price must be greater than or equal to 0")
    private double costPrice;

    @NotNull(message = "Critical Stock is mandatory")
    @Min(value = 0, message = "Critical Stock must be greater than or equal to 0")
    private int criticalStock;
}
