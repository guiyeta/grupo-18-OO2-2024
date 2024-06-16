package com.unla.grupo18.dto;


import com.unla.grupo18.entities.Product;
import com.unla.grupo18.entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchaseDto {

    private int id;
    private String userUsername;
    private String productName;
    private int amount;
    private double totalPrice;
    private LocalDate purchaseDate;
}
