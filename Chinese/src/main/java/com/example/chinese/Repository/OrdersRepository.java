package com.example.chinese.Repository;

import com.example.chinese.Model.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    Orders findOrdersById(Integer id);
    @Query("select a from Orders a where a.status='Shipment has arrived at warehouse'")
    List<Orders> findOrders();

    @Modifying
    @Transactional
    @Query("UPDATE Orders p SET p.status='Shipment has arrived at warehouse' WHERE p.Id= :orderId")
    void updateOrderStatusById(Integer orderId);

    List<Orders> findOrdersByCustomerId(Integer customerId);

    List<Orders> findOrdersByDeliverId1(Integer deliverId);
    List<Orders> findOrdersByStatus(String status);

}

