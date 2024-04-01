package com.example.chinese.Service;

import com.example.chinese.Api.ApiException;
import com.example.chinese.Model.Product;
import com.example.chinese.Model.Supplier;
import com.example.chinese.Repository.ProductRepository;
import com.example.chinese.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        Supplier s = supplierRepository.findSupplierBySupplierId(product.getSupplierId());
        if (s == null) {
            throw new ApiException("  Supplier Id not found");
        }
        productRepository.save(product);
    }

    public void updateProduct(Integer id, Product product) {
        Product product1 = productRepository.findProductById(id);
        if (product1 == null) {
            throw new ApiException("id not found");
        }
        Supplier s = supplierRepository.findSupplierBySupplierId(product.getSupplierId());
        if (s == null) {
            throw new ApiException("  Supplier Id not found");
        }
        product1.setCategory(product.getCategory());
        product1.setDescription(product.getDescription());
        product1.setEvaluation(product.getEvaluation());
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setSupplierId(product.getSupplierId());
        productRepository.save(product1);

    }

    public void deleteProduct(Integer id) {
        Product product1 = productRepository.findProductById(id);
        if (product1 == null) {
            throw new ApiException("id not found");
        }
        productRepository.delete(product1);
    }

    //---------------------------  end CRUD  ---------------------------------

    //------------------------------    1    ----------------------------------
    public Boolean discount(Integer productId, Double discount) {
        Product product1 = productRepository.findProductById(productId);
        if (product1 == null) {
            throw new ApiException("id not found");
        }
        if (product1.getPrice() - discount > 0){
            product1.setPrice(product1.getPrice() - discount);
        productRepository.save(product1);
        return  true ; }
        return false;

    }

    //------------------------------    2    ----------------------------------
//    public List<Product> bestSeller(Integer num) {
//        Product product = new Product();
//        Integer M = 0;
//        List<Product> products = getProduct();
//        List<Product> product1 = new ArrayList<>();
//if (products.size()<num){
//    throw new ApiException("The number required is more than the existing products");
//}
//        int max = products.get(0).getSales();
//        for (int j = 0; j < num; j++) {
//            for (int i = 0; i < products.size() - 1; i++) {
//                if (products.get(i + 1).getSales() > products.get(i).getSales()) {
//                    if (products.get(i + 1).getSales() <= M && products.get(i + 1).getId() != product.getId()) {
//                        max = products.get(i + 1).getSales();
//                        //  product.s = products.get(i + 1);
//                        product.setCategory(products.get(i + 1).getCategory());
//                        product.setDescription(products.get(i + 1).getDescription());
//                        product.setEvaluation(products.get(i + 1).getEvaluation());
//                        product.setName(products.get(i + 1).getName());
//                        product.setPrice(products.get(i + 1).getPrice());
//                        product.setSupplierId(products.get(i + 1).getSupplierId());
//                        productRepository.save(product);
//                    }
//                }
//            }
//            M = product.getSales();
//            product1.add(product);
//        }
//        return product1;
//    }
//

    public List<Product> bestSeller(Integer num) {
        List<Product> products = productRepository.searchTopBySales();
        List<Product> product1 = new ArrayList<>();

        if (products.size()>=num) {
            for (int i = 0; i < num; i++) {
                product1.add(products.get(i));
            }
        }
        return product1;
    }


    //------------------------------    3    ----------------------------------
    public List<Product> bestEvaluation(Integer num) {
        List<Product> products = productRepository.searchTopByEvaluation();
        List<Product> product1 = new ArrayList<>();

        if (products.size()>=num) {
            for (int i = 0; i < num; i++) {
                product1.add(products.get(i));
            }
        }

        return product1;
    }
}