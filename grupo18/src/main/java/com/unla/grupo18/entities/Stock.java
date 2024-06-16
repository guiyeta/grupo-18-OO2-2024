package com.unla.grupo18.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int currentStock;
    private int criticStock;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public void updateStock(int newAmount){
        this.currentStock += newAmount;
    }

    public boolean checkCriticStock(){
        return this.currentStock <=criticStock;
    }


}
