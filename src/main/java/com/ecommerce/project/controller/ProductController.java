package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

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

    @PutMapping ("/product/edit/{id}")
    public ResponseEntity<Response> editProduct(@PathVariable("id") String id, @RequestBody Product updatedProduct) {
        updatedProduct.setId(id);
        try {
            Response response = productService.editProduct(updatedProduct);
            return ResponseEntity.ok().body(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }//done
}
