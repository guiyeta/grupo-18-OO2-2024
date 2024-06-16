package com.unla.grupo18.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPurchaseDtoAdd {


    @NotBlank(message = "Product is mandatory")
    private String productName;


    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private int amount;


}
