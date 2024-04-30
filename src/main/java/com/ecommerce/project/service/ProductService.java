package com.ecommerce.project.service;

import com.ecommerce.project.entity.Product;
import com.ecommerce.project.exception.BadRequestException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.exception.Response;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Response findAll(){
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()){
            return new Response(products, "Success , All Product data is displayed",HttpStatus.OK);
        } else  {
            throw new ResourceNotFoundException("Failed, unable to display all product");
        }
    }

    public Response createProduct(Product product) {
        if (!StringUtils.hasText(product.getName_product())) {
            throw new BadRequestException("The product name cannot be empty.");
        }

        if (product.getCategory() == null) {
            throw new BadRequestException("The category cannot be empty");
        }

        if (!StringUtils.hasText(product.getCategory().getId())) {
            throw new BadRequestException("The category ID cannot be empty");
        }

        categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new BadRequestException(
                        "The category with ID " + product.getCategory().getId() + "was not found in the database"));
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String id = "product" + randomNumber;

        product.setCreatedAt(new Date());
        product.setUpdateAt(null);
        product.setId(id);
        Product createdProduct = productRepository.save(product);

        return new Response(createdProduct, "Product successfully created", HttpStatus.CREATED);

    }



}
