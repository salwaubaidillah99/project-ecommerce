package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.exception.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Response findById(String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category findCategory = categoryOptional.get();
            categoryRepository.findById(id);
            return new Response(findCategory, "The category with " + id + " has been found.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("The category with ID " + id + "was not found");
        }
    }

    public Response findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (!categories.isEmpty()) {
            return new Response(categories, "Success , all category data is displayed", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Failed, unable to display all category data");
        }
    }

    public Response createCategory(Category category) {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String categoryId = "category" + randomNumber;

        category.setCreatedAt(new Date());
        category.setUpdateAt(null);
        category.setId(categoryId);

        Category createdCategory = categoryRepository.save(category);
        return new Response(createdCategory, "Category successfully created", HttpStatus.CREATED);
    }


    public Response deleteById(String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category deletedCategory = categoryOptional.get();
            categoryRepository.deleteById(id);
            return new Response(deletedCategory, "The category with ID " +id+ " has been successfully deleted.", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Category with ID " + id + " not found!");
        }
    }

    public Response editCategory(Category updatedCategory) {
        Optional<Category> optionalExistingCategory = categoryRepository.findById(updatedCategory.getId());
        if (optionalExistingCategory.isPresent()) {
            Category existingCategory = optionalExistingCategory.get();

            existingCategory.setName_category(updatedCategory.getName_category());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setUpdateAt(new Date());
            Category savedCategory = categoryRepository.save(existingCategory);

            return new Response(savedCategory, "The category has been successfully updated", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Category with ID " + updatedCategory.getId() + "not found!");
        }
    }

}