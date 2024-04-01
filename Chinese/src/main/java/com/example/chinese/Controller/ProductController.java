package com.example.chinese.Controller;

import com.example.chinese.Api.ApiResponse;
import com.example.chinese.Model.Product;
import com.example.chinese.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/Product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProduct(){
        return ResponseEntity.status(200).body(productService.getProduct());
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
       productService.addProduct(product);
        return ResponseEntity.ok().body(new ApiResponse("Product added"));

    }
    @PutMapping("/Update/{id}")
    public ResponseEntity UpdateProduct(@PathVariable Integer id, @RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        productService.updateProduct(id, product);

        return ResponseEntity.ok().body(new ApiResponse("Product Update"));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){

        productService.deleteProduct(id);
        return ResponseEntity.ok().body(new ApiResponse("Product Deleted"));

    }
    //---------------------------  end CRUD  ---------------------------------

    // ------------------------------    1    ----------------------------------
   @PutMapping("/discount/{productId}/{discount}")
    public ResponseEntity discount(@PathVariable Integer productId , @PathVariable Double discount){
        Boolean B =productService.discount(productId,discount);
       if (B) {
           return ResponseEntity.ok().body(new ApiResponse("Reduced successfully"));
       }else   return ResponseEntity.status(400).body(new ApiResponse("Reduce not successfully"));
   }


    //------------------------------    2    ----------------------------------
   @GetMapping("/bestSeller/{num}")
    public ResponseEntity bestSeller(@PathVariable Integer num){
        return ResponseEntity.status(200).body( productService.bestSeller(num));
    }

    //------------------------------    3    ----------------------------------
    @GetMapping("/bestEvaluation/{num}")
    public ResponseEntity bestEvaluation(@PathVariable Integer num){
        return ResponseEntity.status(200).body( productService.bestEvaluation(num));
    }
    }

