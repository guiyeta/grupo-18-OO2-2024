package com.unla.grupo18.dto;


import com.unla.grupo18.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {
    private String product;
    private int quantity;
    private LocalDate orderDate;
    private String status;
    private String supplier;
}
