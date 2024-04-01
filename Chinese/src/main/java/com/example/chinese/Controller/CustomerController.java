package com.example.chinese.Controller;

import com.example.chinese.Api.ApiResponse;
import com.example.chinese.Model.Customer;
import com.example.chinese.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/Customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getCustomer(){
        return ResponseEntity.status(200).body(customerService.getCustomer());
    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody @Valid Customer customer, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        customerService.addCustomer(customer);
        return ResponseEntity.ok().body(new ApiResponse("Customer added"));

    }
    @PutMapping("/Update/{id}")
    public ResponseEntity UpdateCustomer(@PathVariable Integer id, @RequestBody @Valid Customer customer , Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        customerService.updateCustomer(id,customer);

        return ResponseEntity.ok().body(new ApiResponse("Customer Update"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id){

       customerService.deleteCustomer(id);
        return ResponseEntity.ok().body(new ApiResponse("Customer Deleted"));

    }
    //---------------------------  end CRUD  ---------------------------------
    //------------------------------    1    ----------------------------------
    @PostMapping("/orderProduct/{customerId}/{productId}")
    public ResponseEntity orderProducts(@PathVariable Integer customerId ,@PathVariable Integer productId ){
        customerService.orderProducts(customerId,productId);
        return ResponseEntity.ok().body(new ApiResponse("The request was completed successfully"));
    }
    //------------------------------    2    ----------------------------------
    @GetMapping("/getStatus/{OrderId}")
    public ResponseEntity statusOfOrder(@PathVariable Integer OrderId){
        return ResponseEntity.status(200).body(customerService.statusOfOrder(OrderId));
    }
    //------------------------------    3    ----------------------------------
    @GetMapping("/getCurrentOrders/{customerId}")
    public ResponseEntity getCurrentOrders(@PathVariable Integer customerId){
        return ResponseEntity.status(200).body(customerService.getCurrentOrders(customerId));
    }
    //------------------------------    4    ----------------------------------
    @GetMapping("/getPreviousOrders/{customerId}")
    public ResponseEntity getPreviousOrders(@PathVariable Integer customerId){
        return ResponseEntity.status(200).body(customerService.getPreviousOrders(customerId));
    }
    //------------------------------    5    ----------------------------------
    @PutMapping("/evaluation/{customerId}/{orderId}/{evaluation}")
    public ResponseEntity evaluation(@PathVariable Integer customerId, @PathVariable Integer orderId, @PathVariable Double evaluation){
        customerService.evaluation(customerId, orderId, evaluation);
        return ResponseEntity.status(200).body(new ApiResponse("evaluation Update"));
    }
    //------------------------------    6    ----------------------------------
    @PutMapping("/gift/{customerId}/{valueOfGift}")
    public ResponseEntity gift(@PathVariable Integer customerId, @PathVariable Integer valueOfGift){
        return ResponseEntity.status(200).body(customerService.gift(customerId,valueOfGift));
    }
}
