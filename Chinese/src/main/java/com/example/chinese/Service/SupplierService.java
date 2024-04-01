package com.example.chinese.Service;

import com.example.chinese.Api.ApiException;
import com.example.chinese.Model.*;
import com.example.chinese.Repository.CustomerRepository;
import com.example.chinese.Repository.OrdersRepository;
import com.example.chinese.Repository.ProductRepository;
import com.example.chinese.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    public List<Supplier> getSupplier() {
        return supplierRepository.findAll();
    }

    public void addSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Integer id, Supplier supplier) {
        Supplier supplier1 = supplierRepository.findSupplierBySupplierId(id);
        if(supplier1==null) {
            throw new ApiException("id not found");
        }
        supplier1.setName(supplier.getName());
        supplier1.setPassword(supplier.getPassword());
        supplier1.setEmail(supplier.getEmail());
        supplier1.setUserName(supplier.getUserName());
        supplier1.setEvaluation(supplier.getEvaluation());
        supplier1.setPhoneNumber(supplier.getPhoneNumber());
       supplierRepository.save(supplier1);

    }

    public void deleteSupplier(Integer id) {
        Supplier supplier1 = supplierRepository.findSupplierBySupplierId(id);
        if (supplier1== null) {
            throw new ApiException("id not found");
        }
    supplierRepository.delete(supplier1);
    }

    //---------------------------  end CRUD  ---------------------------------

    //------------------------------    1    ----------------------------------
    public Boolean evaluation(Integer customerId, Integer orderId, Integer  supplierId,Integer evaluation) {

        Supplier supplier1 = supplierRepository.findSupplierBySupplierId(supplierId);
        if (supplier1== null) {
            throw new ApiException("id not found");
        }
        Orders orders1 = ordersRepository.findOrdersById(orderId);
        if (orders1 == null) {
            throw new ApiException("Product id not found");
        }
        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        if (orders1.getStatus().equals("Delivered")) {
            if (orders1.getCustomerId().equals(customerId)){
                Product product1 = productRepository.findProductById(orders1.getProductId1());
                if (product1.getSupplierId().equals(supplierId)){
                    Double e =  ((supplier1.getEvaluation() + evaluation) / product1.getSales());
                    if (e<=5){
                    supplierRepository.updateSupplierEvaluationById(supplierId,e);
                    return true;

                }
            }

        }
        }
        return false;
    }
    //------------------------------    2    ----------------------------------

    public void updateStatus(Integer orderId ) {
        Orders orders1= ordersRepository.findOrdersById(orderId);
        if(orders1==null) {
            throw new ApiException("order id not found");
        }
           ordersRepository.updateOrderStatusById(orderId);

                }



}
