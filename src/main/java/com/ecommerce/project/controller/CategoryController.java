package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<Response> findAll(){
        Response response = categoryService.findAll();
        return new ResponseEntity<>(response, response.getStatus());
    } //done

    @GetMapping("/category/{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") String id){
        Response response = categoryService.findById(id);
        return new ResponseEntity<>(response, response.getStatus());
    } //done

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable("id") String id) {
        Response response = categoryService.deleteById(id);
        return new ResponseEntity<>(response, response.getStatus());
    } //done

    @PostMapping("/category/create")
    public ResponseEntity<Response> createCategory(@RequestBody Category category) {
        Response response = categoryService.createCategory(category);
        return new ResponseEntity<>(response, response.getStatus());
    }//done

    @PutMapping("/category/edit/{id}")
    public ResponseEntity<Response> editCategory(@PathVariable("id") String id, @RequestBody Category updatedCategory) {
        updatedCategory.setId(id);
        try {
            Response response = categoryService.editCategory(updatedCategory);
            return ResponseEntity.ok().body(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(null, e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }//done
}

