package com.example.chinese.Controller;

import com.example.chinese.Api.ApiResponse;
import com.example.chinese.Model.Supplier;
import com.example.chinese.Service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/Supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;


    @GetMapping("/get")
    public ResponseEntity getSupplier(){
        return ResponseEntity.status(200).body(supplierService.getSupplier());
    }

    @PostMapping("/add")
    public ResponseEntity addSupplier(@RequestBody @Valid Supplier supplier, Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       supplierService.addSupplier(supplier);
        return ResponseEntity.ok().body(new ApiResponse("Supplier added"));

    }
    @PutMapping("/Update/{id}")
    public ResponseEntity UpdateSupplier(@PathVariable Integer id, @RequestBody @Valid Supplier supplier , Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       supplierService.updateSupplier(id,supplier);

        return ResponseEntity.ok().body(new ApiResponse("Supplier Update"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSupplier(@PathVariable Integer id){

        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().body(new ApiResponse("Supplier Deleted"));

    }
    //---------------------------  end CRUD  ---------------------------------

    //------------------------------    1    ----------------------------------
    @PutMapping("/evaluation/{customerId}/{orderId}/{supplierId}/{evaluation}")
    public ResponseEntity evaluation(@PathVariable Integer customerId, @PathVariable Integer orderId, @PathVariable Integer  supplierId,@PathVariable Integer evaluation){

        Boolean B = supplierService.evaluation(customerId, orderId, supplierId, evaluation);
if (B) {
    return ResponseEntity.ok().body(new ApiResponse("evaluation Updated"));
}else   return ResponseEntity.status(400).body(new ApiResponse("evaluation not Update"));
    }

    @PutMapping("/updateStatus/{orderId}")
    public ResponseEntity updateStatus(@PathVariable Integer orderId){
        supplierService.updateStatus(orderId);
        return ResponseEntity.status(200).body(new ApiResponse("status Updated"));
    }
}
