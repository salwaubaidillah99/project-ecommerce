package com.ecommerce.project.repository;

import com.ecommerce.project.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, String> {
}
