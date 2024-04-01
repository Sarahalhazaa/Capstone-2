package com.example.chinese.Repository;

import com.example.chinese.Model.Orders;
import com.example.chinese.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findProductById(Integer ID);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.evaluation = :evaluation WHERE p.Id = :productId")
    void updateProductEvaluationById(Integer productId, Double evaluation);

    @Query("SELECT p FROM Product p ORDER BY p.sales DESC")
    List<Product> searchTopBySales();

    @Query("SELECT p FROM Product p ORDER BY p.evaluation DESC")
    List<Product> searchTopByEvaluation();
}
