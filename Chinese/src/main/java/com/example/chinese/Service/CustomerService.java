package com.example.chinese.Service;

import com.example.chinese.Api.ApiException;
import com.example.chinese.Model.Customer;
//import com.example.chinese.Model.Orders;
//import com.example.chinese.Model.Product;
import com.example.chinese.Model.Orders;
import com.example.chinese.Model.Product;
import com.example.chinese.Repository.CustomerRepository;
//import com.example.chinese.Repository.ProductRepository;
import com.example.chinese.Repository.OrdersRepository;
import com.example.chinese.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;
    // Map<Integer,Integer> orders =new HashMap<>();
    ArrayList<Orders> orders = new ArrayList<>();
    ArrayList<Orders> delivery = new ArrayList<>();

    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {

        customerRepository.save(customer);
    }

    public void updateCustomer(Integer id, Customer customer) {
        Customer customer1 = customerRepository.findCustomerByCustomerId(id);
        if (customer1 == null) {
            throw new ApiException("id not found");
        }
        customer1.setAdders(customer.getAdders());
        customer1.setPassword(customer.getPassword());
        customer1.setEmail(customer.getEmail());
        customer1.setName(customer.getName());
        customer1.setUserName(customer.getUserName());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customer1.setAge(customer.getAge());
        customer1.setPortfolio(customer.getPortfolio());
        customerRepository.save(customer1);

    }

    public void deleteCustomer(Integer id) {
        Customer customer1 = customerRepository.findCustomerByCustomerId(id);
        if (customer1 == null) {
            throw new ApiException("id not found");
        }
        customerRepository.delete(customer1);
    }

    //---------------------------  end CRUD  ---------------------------------


    //------------------------------    1    ----------------------------------
    public void orderProducts(Integer customerId, Integer productId) {
        Orders order1 = new Orders();
        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        Product product1 = productRepository.findProductById(productId);
        if (product1 == null) {
            throw new ApiException("Product id not found");
        }
        order1.setProductId1(productId);
        order1.setCustomerId(customerId);
        ordersService.taxCalculation(product1.getPrice());
        order1.setTax(ordersService.taxCalculation(product1.getPrice()));
        order1.setTotalPrice(order1.getTax() + product1.getPrice() + order1.getDeliveryPrice());
        order1.setDeliveryPrice(order1.getDeliveryPrice());
        order1.setStatus("Order is being executed");
        product1.setSales(product1.getSales() + 1);


        ordersService.addOrders(order1);
        orders.add(order1);
        product1.setSales(product1.getSales() + 1);
        delivery.add(order1);

    }

    //------------------------------    2    ----------------------------------
    public String statusOfOrder(Integer orderId) {
       Orders orders1 = ordersRepository.findOrdersById(orderId);
//        if (orders1 == null) {
//            throw new ApiException("order id not found");
//        }

        return orders1.getStatus();
    }

    //------------------------------    3    ----------------------------------
    public ArrayList<Orders> getCurrentOrders(Integer customerId) {
        ArrayList<Orders> orders2 = new ArrayList<>();

        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        List<Orders> orders3 = ordersRepository.findOrdersByCustomerId(customerId);
        if (orders3== null) {
            throw new ApiException(" no current orders");
        }
        for (Orders orders1 : orders3) {
                if (orders1.getStatus().equalsIgnoreCase("Order is being executed")||orders1.getStatus().equals("Shipment has arrived at warehouse")||orders1.getStatus().equals("Delivery is in progress")){
                    orders2.add(orders1);
            }}

        return orders2;

    }
    //------------------------------    4    ----------------------------------

    public ArrayList<Orders> getPreviousOrders(Integer customerId) {
        ArrayList<Orders> orders2 = new ArrayList<>();

        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        List<Orders> orders3 = ordersRepository.findOrdersByCustomerId(customerId);
        if (orders3== null) {
            throw new ApiException(" no current orders");
        }
        for (Orders orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Delivered")){
                orders2.add(orders1);
            }}

        return orders2;
    }
    //------------------------------    5    ----------------------------------
    public void evaluation(Integer customerId, Integer orderId, Double evaluation) {

        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        Orders orders1 = ordersRepository.findOrdersById(orderId);
        if (orders1 == null) {
            throw new ApiException(" Order id not found");
        }
        if (orders1.getStatus().equalsIgnoreCase("Delivered")) {
            if (orders1.getCustomerId().equals(customerId)){
            Product product1 = productRepository.findProductById(orders1.getProductId1());
                if (product1.getSales() == 0) {
                    throw new ApiException(" Sales = 0");
                }
            Double e =  ( (product1.getEvaluation()+evaluation) / product1.getSales());
            productRepository.updateProductEvaluationById(orders1.getProductId1(),e);


        }}}

    //------------------------------    6    ----------------------------------
    public StringBuilder gift(Integer customerId, Integer valueOfGift) {
        Customer customer1 = customerRepository.findCustomerByCustomerId(customerId);
        Random random = new Random();
     //   Integer randomNumber = 0;
        if (customer1 == null) {
            throw new ApiException("customer id not found");
        }
        StringBuilder randomVariable = new StringBuilder();
        if (customer1.getPortfolio()>=valueOfGift) {
//            randomNumber = random.nextInt(9000000)+1000000;

            for (int i = 0; i < 7; i++) {
                randomVariable.append(random.nextInt(10));
            }

                Integer u = (customer1.getPortfolio()-valueOfGift);
                customerRepository.updateCustomerPortfolioByById(customerId, u);
                customerRepository.save(customer1);
            }

        return randomVariable;
    }




}