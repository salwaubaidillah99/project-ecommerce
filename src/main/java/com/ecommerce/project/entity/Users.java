package com.ecommerce.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity

public class Users implements Serializable {
    @Id
    private String id;
    private String email;
    private String address;
    private String telephone_number;
    private String fullname;
    private String username;
    private String password;
    private String user_role;
    private boolean is_Active;
}
