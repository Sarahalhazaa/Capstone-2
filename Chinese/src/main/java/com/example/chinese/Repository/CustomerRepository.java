package com.example.chinese.Repository;

import com.example.chinese.Model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerByCustomerId(Integer CustomerId);
    @Modifying
    @Transactional
    @Query("UPDATE Customer p SET p.Portfolio = :portfolio WHERE p.customerId = :customerId")
    void updateCustomerPortfolioByById(Integer customerId, Integer portfolio);
}
