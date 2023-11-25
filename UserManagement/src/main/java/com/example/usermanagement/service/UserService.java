package com.example.usermanagement.service;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.service.UserServiceDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserServiceDto> findByEmail(String email);

    Optional<UserServiceDto> findByPhoneNumber(String phoneNumber);

    List<UserServiceDto> findAll();

    List<UserServiceDto> findByFirstName(String firstName);

    List<UserServiceDto> findByLastName(String lastName);

    List<UserServiceDto> findByBirthDate(Date birthDate);

    UserServiceDto findById(Long id);

    Long register(UserRegisterDto userRegisterDto);

    UserServiceDto updateUser(Long id, UserServiceDto user);

    boolean deleteUser(Long id);
}
