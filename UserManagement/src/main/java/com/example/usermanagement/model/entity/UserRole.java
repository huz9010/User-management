package com.example.usermanagement.model.entity;

import com.example.usermanagement.model.enumeration.UserRoleEnum;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "user_roles")
@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users;

    public UserRole() {
    }

    public UserRole(UserRoleEnum role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
