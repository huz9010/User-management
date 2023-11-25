package com.example.usermanagement.service;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.service.UserServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.Optional;

public interface UserService {

    Optional<UserServiceDto> findByEmail(String email);

    Optional<UserServiceDto> findByPhoneNumber(String phoneNumber);

    Page<UserServiceDto> findAll(Pageable pageable);

    Page<UserServiceDto> findByFirstName(String firstName, Pageable pageable);

    Page<UserServiceDto> findByLastName(String lastName, Pageable pageable);

    Page<UserServiceDto> findByBirthDate(LocalDate birthDate, Pageable pageable);

    UserServiceDto findById(Long id);

    Long register(UserRegisterDto userRegisterDto);

    boolean updateUser(Long id, UserServiceDto user);

    boolean deleteUser(Long id);
}
