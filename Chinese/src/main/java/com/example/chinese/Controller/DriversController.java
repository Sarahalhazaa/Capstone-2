package com.example.chinese.Controller;

import com.example.chinese.Api.ApiResponse;
import com.example.chinese.Model.Drivers;
import com.example.chinese.Service.DriversService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/Drivers")
@RequiredArgsConstructor
public class DriversController {
    private final DriversService driversService;

    @GetMapping("/get")
    public ResponseEntity getDriver(){
        return ResponseEntity.status(200).body(driversService.getDrivers());
    }

    @PostMapping("/add")
    public ResponseEntity addDriver(@RequestBody @Valid Drivers drivers, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        driversService.addDrivers(drivers);
        return ResponseEntity.ok().body(new ApiResponse("Driver added"));

    }
    @PutMapping("/Update/{id}")
    public ResponseEntity UpdateDriver(@PathVariable Integer id, @RequestBody @Valid Drivers drivers, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       driversService.updateDrivers(id,drivers);

        return ResponseEntity.ok().body(new ApiResponse("Driver Update"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDriver(@PathVariable Integer id){

       driversService.deleteDrivers(id);
        return ResponseEntity.ok().body(new ApiResponse("Driver Deleted"));

    }
    //---------------------------  end CRUD  ---------------------------------

    //------------------------------    1    ----------------------------------
    @GetMapping("/ordersDelivery")
    public ResponseEntity ordersDelivery(){
        return ResponseEntity.status(200).body(driversService.findOrders());
    }
    //------------------------------    2    ----------------------------------
    @PostMapping("/delivery/{driverId}/{orderId}")
    public ResponseEntity delivery(@PathVariable Integer driverId , @PathVariable Integer orderId){
        driversService.delivery(driverId,orderId);
        return ResponseEntity.status(200).body(new ApiResponse("The task of delivering the order was assigned to the driver"));
    }
    //------------------------------    3    ----------------------------------
    @PutMapping("/updateStatus/{driverId}/{orderId}")
    public ResponseEntity updateStatus(@PathVariable Integer driverId , @PathVariable Integer orderId){

        Boolean o = driversService.updateStatus(driverId,orderId);
        if(o){
        return ResponseEntity.status(200).body(new ApiResponse("order has been delivered"));}
        else  return ResponseEntity.status(400).body(new ApiResponse("The order was not delivered"));
    }
    //------------------------------    4    ----------------------------------
    @GetMapping("/previousOrders/{driverId}")
    public ResponseEntity previousOrders(@PathVariable Integer driverId){
        return ResponseEntity.status(200).body(driversService.previousOrders(driverId));
    }
    //------------------------------    5    ----------------------------------
    @PutMapping("/bonus/{driverId}/{numberOfOrders}/{bonus}")
    public ResponseEntity bonus(@PathVariable Integer driverId, @PathVariable Integer numberOfOrders ,@PathVariable Integer bonus){
       Boolean o =driversService.bonus(driverId,numberOfOrders,bonus);
        if(o)
            return ResponseEntity.status(200).body(new ApiResponse("Bonus added"));
        else  return ResponseEntity.status(400).body(new ApiResponse("Bonus has not been added"));
    }
}
