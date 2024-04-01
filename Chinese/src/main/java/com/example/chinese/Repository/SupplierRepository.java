package com.example.chinese.Repository;

import com.example.chinese.Model.Supplier;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
Supplier findSupplierBySupplierId(Integer ID);

    @Modifying
    @Transactional
    @Query("UPDATE Supplier p SET p.evaluation = :evaluation WHERE p.supplierId = :supplierId")
    void updateSupplierEvaluationById(Integer supplierId, Double evaluation);
}
