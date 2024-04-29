package com.ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class ShoppingCart implements Serializable {
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Product product;
    @JoinColumn
    @ManyToOne
    private Users users;
    private double quantity;
    private BigDecimal unit_price;
    private  BigDecimal amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
