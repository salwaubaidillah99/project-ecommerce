package com.ecommerce.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Category implements Serializable {
    @Id
    private String id;
    private String name_category;
    private String description;
}
