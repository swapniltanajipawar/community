package com.jaspersoft.community.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jaspersoft.community.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}


