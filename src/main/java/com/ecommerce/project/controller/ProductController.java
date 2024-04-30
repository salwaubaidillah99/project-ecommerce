package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Response> findAll(){
        Response response = productService.findAll();
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PostMapping("/product/create")
    public ResponseEntity<Response> createProduct(@RequestBody Product product) {
        Response response = productService.createProduct(product);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
