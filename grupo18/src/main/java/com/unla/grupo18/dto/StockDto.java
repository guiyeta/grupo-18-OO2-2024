package com.unla.grupo18.dto;

import com.unla.grupo18.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private int currentStock;
    private int criticStock;
    private String productName;
}
