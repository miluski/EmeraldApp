package com.miluski.products.campaignes.backend.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miluski.products.campaignes.backend.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
