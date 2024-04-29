package com.ecommerce.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class OrderLog implements Serializable {
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Orders order;
    @JoinColumn
    @ManyToOne
    private  Users users;
    private Integer type_of_log;
    private String message_log;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
