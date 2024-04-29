package com.ecommerce.project.entity;

import com.ecommerce.project.models.StatusOrders;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Orders implements Serializable {
    @Id
    private String id;
    private String order_number;
    @Temporal(TemporalType.DATE)
    private Date order_date;
    @JoinColumn
    @ManyToOne
    private Users users;
    private String shipping_address;
    private BigDecimal total_number_of_transactions;
    private BigDecimal shipping_cost;
    private BigDecimal total_orders;
    @Enumerated(EnumType.STRING)
    private StatusOrders statusOrders;
    @Temporal(TemporalType.TIMESTAMP)
    private Date order_time;



}
