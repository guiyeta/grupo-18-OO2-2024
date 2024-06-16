package com.unla.grupo18.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDto {

    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private int amount;

    @NotNull(message = "OrderDate  is mandatory")
    private LocalDate orderDate;

    private String status;

    private String totalPrice;

    @NotBlank(message = "Supplier  is mandatory")
    private String supplier;

}
