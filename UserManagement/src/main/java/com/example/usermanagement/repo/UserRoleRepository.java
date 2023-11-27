package com.example.usermanagement.repo;

import com.example.usermanagement.model.entity.UserRole;
import com.example.usermanagement.model.enumeration.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

     UserRole findByRole(UserRoleEnum name);
}
