package com.example.usermanagement.service;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.dto.UserServiceDto;
import com.example.usermanagement.model.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    Page<UserServiceDto> findByEmail(String email, Pageable pageable);

    Optional<UserServiceDto> findByEmail(String email);

    Page<UserServiceDto> findByPhoneNumber(String phoneNumber, Pageable pageable);

    Optional<UserServiceDto> findByPhoneNumber(String phoneNumber);

    Page<UserServiceDto> findAll(Pageable pageable);

    Page<UserServiceDto> findByFirstName(String firstName, Pageable pageable);

    Page<UserServiceDto> findByLastName(String lastName, Pageable pageable);

    Page<UserServiceDto> findByBirthDate(LocalDate birthDate, Pageable pageable);

    UserServiceDto findById(Long id);

    String register(UserRegisterDto userRegisterDto);

    boolean updateUser(Long id, UserUpdateDto user);

    boolean deleteUser(Long id);

    boolean isCurrentUser(Long userId);

    <U> boolean userExists(U user);
}
