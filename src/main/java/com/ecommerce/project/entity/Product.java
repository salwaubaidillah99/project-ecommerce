package com.ecommerce.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class Product implements Serializable {
    @Id
    private String id;
    private String name_product;
    private String image;
    private BigDecimal price;
    private double stock_quantity;
    @JoinColumn
    @ManyToOne
    private Category category;
}
