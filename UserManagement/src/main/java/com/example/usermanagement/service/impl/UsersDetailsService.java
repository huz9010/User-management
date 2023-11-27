package com.example.usermanagement.service.impl;

import com.example.usermanagement.model.entity.UserEntity;
import com.example.usermanagement.model.entity.UserRole;
import com.example.usermanagement.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsersDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public UsersDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(email)
        .map(UsersDetailsService::map)
        .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
  }

  private static UserDetails map(UserEntity user) {
    return User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(map(user.getRole()))
        .build();
  }

  private static GrantedAuthority map(UserRole userRole) {
    return new SimpleGrantedAuthority(
        "ROLE_" + userRole.getRole().name());
  }
}
