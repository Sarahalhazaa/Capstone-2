package com.example.chinese.Service;

import com.example.chinese.Api.ApiException;
import com.example.chinese.Model.Customer;
import com.example.chinese.Model.Drivers;
import com.example.chinese.Model.Orders;
import com.example.chinese.Repository.DriversRepository;
import com.example.chinese.Repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DriversService {
    private final DriversRepository driversRepository;
    public final CustomerService customerService;
    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;
    Map<Integer,Orders> orders11 = new HashMap<>();

    public List<Drivers> getDrivers() {
        return driversRepository.findAll();
    }

    public void addDrivers(Drivers drivers) {
        driversRepository.save(drivers);
    }

    public void updateDrivers(Integer id, Drivers drivers) {
        Drivers drivers1 = driversRepository.findDriversByDriverId(id);
        if(drivers1==null) {
            throw new ApiException("id not found");
        }
        drivers1.setName(drivers.getName());
        drivers1.setPassword(drivers.getPassword());
        drivers1.setEmail(drivers.getEmail());
        drivers1.setAge(drivers.getAge());
        drivers1.setUserName(drivers.getUserName());
        drivers1.setEvaluation(drivers.getEvaluation());
        drivers1.setPhoneNumber(drivers.getPhoneNumber());
      driversRepository.save(drivers1);

    }

    public void deleteDrivers(Integer id) {
        Drivers drivers1 = driversRepository.findDriversByDriverId(id);
        if (drivers1 == null) {
            throw new ApiException("id not found");
        }
        driversRepository.delete(drivers1);
    }

    //---------------------------  end CRUD  ---------------------------------
//    public ArrayList<Orders> ordersDelivery() {
//        ArrayList<Orders> orders1 = new ArrayList<>();
//        for (Orders orders : customerService.orders)
//       if (orders.getStatus().equalsIgnoreCase("Shipment has arrived at warehouse")){
//           orders1.add(orders);
//       }
//       return orders1;
//    }

    //------------------------------    1    ----------------------------------
    public List<Orders> findOrders() {
        List<Orders> orders = ordersRepository.findOrders();
        if (orders == null) {
            throw new ApiException("Order not found");
        }
        return orders;
    }
    //------------------------------    2    ----------------------------------
    public void delivery(Integer driverId ,Integer orderId ) {
        Orders orders1= ordersRepository.findOrdersById(orderId);
        if(orders1==null) {
            throw new ApiException("order id not found");
        }
        Drivers drivers1 = driversRepository.findDriversByDriverId(driverId);
        if (drivers1 == null) {
            throw new ApiException("driver id not found");
        }
        if (orders1.getStatus().equalsIgnoreCase("Shipment has arrived at warehouse")){
            orders1.setStatus("Delivery is in progress");
            ordersRepository.save(orders1);
            drivers1.setDeliveryOrders(drivers1.getDeliveryOrders()+1);
            driversRepository.save(drivers1);
                    orders11.put(driverId,orders1);

                }
            }
    //------------------------------    3    ----------------------------------
    public Boolean updateStatus(Integer driverId ,Integer orderId ) {
        Orders orders1= ordersRepository.findOrdersById(orderId);
        if(orders1==null) {
            throw new ApiException("order id not found");
        }
        Drivers drivers1 = driversRepository.findDriversByDriverId(driverId);
        if (drivers1 == null) {
            throw new ApiException("driver id not found");
        }
        if (orders1.getStatus().equalsIgnoreCase("Delivery is in progress")){
                     orders1.setStatus("Delivered");
                     ordersRepository.save(orders1);
                     drivers1.setDeliveryOrders(drivers1.getDeliveryOrders()+1);
                     driversRepository.save(drivers1);
                                  return true;
                             }
return false;
                }

    //------------------------------    4    ----------------------------------

    public List<Orders> previousOrders(Integer driverId) {
        ArrayList<Orders> orders2 = new ArrayList<>();
        Drivers drivers = driversRepository.findDriversByDriverId(driverId);
        if (drivers == null) {
            throw new ApiException("driver id not found");
        }
        List<Orders> orders3 = ordersRepository.findOrdersByDeliverId1(driverId);
        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (Orders orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Delivered")){
                orders2.add(orders1);
            }}

        return orders2;
    }

    //------------------------------    5    ----------------------------------
    public Boolean bonus(Integer driverId ,Integer numberOfOrders ,Integer bonus ) {
        List<Orders> drivers= previousOrders(driverId);
        int count=drivers.size();
                    if(count>=numberOfOrders){
                        Drivers drivers1 = driversRepository.findDriversByDriverId(driverId);
                        drivers1.setBonusBalance(drivers1.getBonusBalance()+bonus);
                        driversRepository.save(drivers1);
                        return true;
                 }
        return false;
    }



            }



