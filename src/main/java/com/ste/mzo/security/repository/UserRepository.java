package com.ste.mzo.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ste.mzo.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
