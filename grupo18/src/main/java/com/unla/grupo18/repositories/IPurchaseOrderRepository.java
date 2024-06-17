package com.unla.grupo18.repositories;

import com.unla.grupo18.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Serializable> {

    public abstract Optional<PurchaseOrder> findById(Long id);
    @Query("SELECT po FROM PurchaseOrder po WHERE po.product.id = :productId AND po.status = 'PENDING'")
    List<PurchaseOrder> findByProductIdAndStatusPending(Long productId);
}
