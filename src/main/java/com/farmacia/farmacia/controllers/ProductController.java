package com.farmacia.farmacia.controllers;

import com.farmacia.farmacia.dtos.ProductRecordDto;
import com.farmacia.farmacia.exceptions.ProductNotFoundException;
import com.farmacia.farmacia.models.ProductModel;
import com.farmacia.farmacia.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/farmacia/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@Valid @RequestBody ProductRecordDto productRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productRecordDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProduct (){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getIdProduct(@PathVariable UUID id){
        try {
            ProductModel product = productService.getIdProduct(id);
            return ResponseEntity.ok(product);
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdProduct(@PathVariable UUID id){
        try {
            productService.deleteIdProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

//    @PostMapping("/sell")
//    public String registerSale (@RequestParam(name = "quantitySold", required = false, defaultValue = "0") int quantitySold){
//        return "Venda registrada com sucesso";
//    }

    @PostMapping("/{id}/sell")
    public ResponseEntity<?> sellProduct(@PathVariable UUID id,@RequestParam int quantitySold ){
        try {
            ProductModel updatedProduct = productService.sellProduct(id, quantitySold);
            return ResponseEntity.ok(updatedProduct);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/{id}/addStock")
    public ProductModel addStock(@PathVariable UUID id, @RequestParam (name = "quantityAdded") int quantityAdded){
        return productService.addStock(id,quantityAdded);
    }
}
