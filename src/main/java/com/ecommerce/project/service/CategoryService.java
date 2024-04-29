package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(String id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("\"The category with ID" + id + "was not found"));
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category createdCategory(Category category){
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String categoryId = "category" + randomNumber;

        category.setCreatedAt(new Date());
        category.setUpdateAt(null);
        category.setId(categoryId);
        return categoryRepository.save(category);
    }

    public void deleteById (String id){
        categoryRepository.deleteById(id);
    }

    public Category editCategory(Category updatedCategory) {
        Optional<Category> optionalExistingCategory = categoryRepository.findById(updatedCategory.getId());
        if (optionalExistingCategory.isPresent()){
            Category existingCategory = optionalExistingCategory.get();
            if (!existingCategory.equals(updatedCategory)){
                existingCategory.setName_category(updatedCategory.getName_category());
                existingCategory.setDescription(updatedCategory.getDescription());
                existingCategory.setUpdateAt(new Date());
                return categoryRepository.save(existingCategory);
            } else {
                return existingCategory;
            }
        } else {
            return null;
        }
    }
}
