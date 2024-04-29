package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Product findById(String id){
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The Product with ID" + id + "was not found"));
    }
    public Product createdProduct(Product product){
        return productRepository.save(product);
    }
    public Product editProduct(Product product){
        return productRepository.save(product);
    }
    public Product editImage(String id, String image){
        Product product = findById(id);
        product.setImage(image);
        return productRepository.save(product);
    }
    public  void deleteById(String id){
        productRepository.deleteById(id);
    }
}
