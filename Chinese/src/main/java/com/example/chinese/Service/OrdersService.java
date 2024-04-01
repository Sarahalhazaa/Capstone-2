package com.example.chinese.Service;

import com.example.chinese.Api.ApiException;
import com.example.chinese.Model.Customer;
import com.example.chinese.Model.Orders;
import com.example.chinese.Model.Product;
import com.example.chinese.Model.Supplier;
import com.example.chinese.Repository.CustomerRepository;
import com.example.chinese.Repository.OrdersRepository;
import com.example.chinese.Repository.ProductRepository;
import com.example.chinese.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    public List<Orders> getOrders() {
        return ordersRepository.findAll();
    }

    public void addOrders( Orders orders) {
        Customer s =customerRepository.findCustomerByCustomerId(orders.getCustomerId());
        if (s==null){
            throw new ApiException(" Customer Id not found");
        }
        Product p = productRepository.findProductById(orders.getProductId1());
        if (p==null){
            throw new ApiException("Product Id not found");}

        ordersRepository.save(orders);
    }

    public void updateOrders(Integer id, Orders orders) {
        Orders orders1= ordersRepository.findOrdersById(id);
        if(orders1==null) {
            throw new ApiException("id not found");
        }
        Customer s =customerRepository.findCustomerByCustomerId(orders.getCustomerId());
        if (s==null){
            throw new ApiException(" Customer Id not found");
        }
        Product p = productRepository.findProductById(orders.getProductId1());
        if (p==null){
            throw new ApiException("Product Id not found");}
      //  orders1.setOrderDate(orders.getOrderDate());
        orders1.setTax(orders.getTax());
        orders1.setDeliveryPrice(orders.getDeliveryPrice());
        orders1.setStatus(orders.getStatus());
        orders1.setCustomerId(orders.getCustomerId());
        orders1.setProductId1(orders.getProductId1());
        orders1.setTotalPrice(orders.getTotalPrice());
     //   orders1.setDeliveryDate(orders.getDeliveryDate());
        ordersRepository.save(orders1);

    }

    public void deleteOrders(Integer id) {
        Orders orders1= ordersRepository.findOrdersById(id);
        if (orders1 == null) {
            throw new ApiException("id not found");
        }
        ordersRepository.save(orders1);
    }

    //---------------------------  end CRUD  ---------------------------------

    //------------------------------    1    ----------------------------------
    public Double taxCalculation(Double PriceOfProduct) {
        Double tax = PriceOfProduct * 0.15;
        return  tax;
    }
}
