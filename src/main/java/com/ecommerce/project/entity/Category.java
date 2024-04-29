package com.ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Data
public class Category implements Serializable {

    @Id
    private String id;
    private String name_category;
    private String description;
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
