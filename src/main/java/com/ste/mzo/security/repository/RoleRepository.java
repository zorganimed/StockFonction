package com.ste.mzo.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ste.mzo.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    
    @Query("select count(*) from Role r ")
    int countRole();
}
