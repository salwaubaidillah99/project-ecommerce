package com.ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @PrePersist
    protected void  onCreate(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        createdAt= new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        updateAt= new Date();
    }
}
