package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
    Users findByUsername(String username);

    boolean existsByEmailAndIdNot(String email, String id);

    boolean existsByTelephoneAndIdNot(String telephone, String id);

    boolean existsByUsernameAndIdNot(String username, String id);
}
