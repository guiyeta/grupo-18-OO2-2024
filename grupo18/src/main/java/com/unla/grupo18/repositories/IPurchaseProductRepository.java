package com.unla.grupo18.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.unla.grupo18.entities.PurchaseProduct;

@Repository
public interface IPurchaseProductRepository extends JpaRepository <PurchaseProduct, Serializable>{
	public abstract Optional<PurchaseProduct> findById(Long id);
	
	
}
