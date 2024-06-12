package com.unla.grupo18.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LotDto {

    private LocalDate ReceptionDate; // No si dejarla LocalDate o string;
    private String supplier;
    private int amount ;
    private double purchasePrice;
    private String product; //Lo obtengo con el stock de la clase Lot. (lot.getProduct().getName();
}
