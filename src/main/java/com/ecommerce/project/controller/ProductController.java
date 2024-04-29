package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    public Product findById(@PathVariable("id") String id){
        return productService.findById(id);
    }

    @PostMapping("/product/create")
    public Product create(@RequestBody Product product){
        return productService.createdProduct(product);
    }
}
