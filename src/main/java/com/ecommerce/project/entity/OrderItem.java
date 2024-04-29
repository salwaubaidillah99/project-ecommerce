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

public class OrderItem implements Serializable {
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Orders order;
    @JoinColumn
    @ManyToOne
    private Product product;
    private String description;
    private Double total_number_of_items;
    private BigDecimal unit_price;
    private BigDecimal total_price;

}
