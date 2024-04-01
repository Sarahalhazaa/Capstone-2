package com.example.chinese.Controller;

import com.example.chinese.Api.ApiResponse;
import com.example.chinese.Model.Orders;
import com.example.chinese.Service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/Orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @GetMapping("/get")
    public ResponseEntity getOrder(){
        return ResponseEntity.status(200).body(ordersService.getOrders());
    }

    @PostMapping("/add")
    public ResponseEntity addOrder(@RequestBody @Valid Orders orders , Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       ordersService.addOrders(orders);
        return ResponseEntity.ok().body(new ApiResponse("Order added"));

    }
    @PutMapping("/Update/{id}")
    public ResponseEntity UpdateOrder(@PathVariable Integer id, @RequestBody @Valid Orders orders, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        ordersService.updateOrders(id,orders);

        return ResponseEntity.ok().body(new ApiResponse("Order Update"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Integer id){

       ordersService.deleteOrders(id);
        return ResponseEntity.ok().body(new ApiResponse("Order Deleted"));

    }
    //---------------------------  end CRUD  ---------------------------------
}
