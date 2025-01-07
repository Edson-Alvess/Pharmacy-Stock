package com.farmacia.farmacia.services;

import com.farmacia.farmacia.dtos.ProductRecordDto;
import com.farmacia.farmacia.exceptions.ProductNotFoundException;
import com.farmacia.farmacia.models.ProductModel;
import com.farmacia.farmacia.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public ProductModel saveProduct(ProductRecordDto productRecordDto){
        ProductModel product = new ProductModel();
        product.setName(productRecordDto.name());
        product.setValue(productRecordDto.value());
        product.setDescription(productRecordDto.description());
        product.setQuantity(productRecordDto.quantity());
        return productRepository.save(product);
    }

    public List<ProductModel> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductModel getIdProduct(UUID id){
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with ID: " + id));
    }

    public void deleteIdProduct (UUID id){
        ProductModel product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with ID: " + id));
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductModel sellProduct(UUID id, int quantitySold){
        ProductModel product = getIdProduct(id);

        if (product.getQuantity() < quantitySold){
            throw new IllegalArgumentException("Sale cannot be made: quantity requested (" + quantitySold + ") is greater than the available stock (" + product.getQuantity() + ") for the product: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - quantitySold);
        return productRepository.save(product);
    }

    @Transactional
    public ProductModel addStock(UUID id, int quantityAdded){
        ProductModel product = getIdProduct(id);
        product.setQuantity(product.getQuantity() + quantityAdded);
        return productRepository.save(product);
    }
}
