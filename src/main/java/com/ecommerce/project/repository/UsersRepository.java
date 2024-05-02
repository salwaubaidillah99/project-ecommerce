package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
}
