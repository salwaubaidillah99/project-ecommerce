package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public Category findById(@PathVariable("id") String id){
        return categoryService.findById(id);
    }

    @PostMapping("/category/create")
    public Category create(@RequestBody Category category){
        return categoryService.createdCategory(category);
    }

    @PutMapping("/category/edit/{id}")
    public Category edit(@PathVariable("id") String id, @RequestBody Category updatedCategory){
        updatedCategory.setId(id);
        return  categoryService.editCategory(updatedCategory);
    }

}
