package com.unla.grupo18.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String code;
    private String description;
    private String name;
    private double costPrice;
    private double sellPrice;
    private int criticalStock;
    private boolean active;
}
