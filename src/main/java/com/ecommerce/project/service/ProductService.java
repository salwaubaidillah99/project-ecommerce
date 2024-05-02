package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
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
import java.util.Optional;
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

    public Response editProduct(Product product) {
        if (!StringUtils.hasText(product.getName_product())) {
            throw new BadRequestException("Nama produk tidak boleh kosong.");
        }

        if (product.getCategory() == null || !StringUtils.hasText(product.getCategory().getId())) {
            throw new BadRequestException("ID kategori produk tidak boleh kosong");
        }

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Kategori dengan ID " + product.getCategory().getId() + " tidak ditemukan dalam database"));

        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produk dengan ID " + product.getId() + " tidak ditemukan!"));

        existingProduct.setName_product(product.getName_product());
        existingProduct.setStock_quantity(product.getStock_quantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setUpdateAt(new Date());

        existingProduct.setCategory(category);
        Product editedProduct = productRepository.save(existingProduct);

        return new Response(editedProduct, "Produk berhasil diperbarui", HttpStatus.OK);
    }

    public  Response findById (String id){
        Optional<Product>productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product findProduct = productOptional.get();
            productRepository.findById(id);
            return  new Response(findProduct, "The product with ID " +id+ " has been found", HttpStatus.OK);
        } else  {
            throw new ResourceNotFoundException("The product with ID " +id+ " not found!");
        }
    }

    public Response deleteById(String id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product deletedProduct = productOptional.get();
            productRepository.deleteById(id);
            return  new Response(deletedProduct, "The product with ID" +id+ " has been successfullt deleted.", HttpStatus.OK);
        } else  {
            throw new ResourceNotFoundException("Product with ID" +id+ "not found!");
        }
    }




}
