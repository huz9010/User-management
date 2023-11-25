package com.example.usermanagement.service.impl;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.entity.User;
import com.example.usermanagement.model.service.UserServiceDto;
import com.example.usermanagement.repo.UserRepository;
import com.example.usermanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<UserServiceDto> findByEmail(String email) {
        return Optional.ofNullable(this.modelMapper.map(this.userRepository.findByEmailContainsIgnoreCase(email), UserServiceDto.class));
    }

    @Override
    public Optional<UserServiceDto> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(this.modelMapper.map(this.userRepository.findByPhoneNumberContainsIgnoreCase(phoneNumber), UserServiceDto.class));
    }

    @Override
    public List<UserServiceDto> findAll() {
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceDto> findByFirstName(String firstName) {
        return this.userRepository.findAllByFirstNameContainsIgnoreCase(firstName).stream()
                .map(u -> this.modelMapper.map(u, UserServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceDto> findByLastName(String lastName) {
        return this.userRepository.findAllByLastNameContainsIgnoreCase(lastName).stream()
                .map(u -> this.modelMapper.map(u, UserServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceDto> findByBirthDate(Date birthDate) {
        return this.userRepository.findAllByBirthDate(birthDate).stream()
                .map(u -> this.modelMapper.map(u, UserServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceDto findById(Long id) {
        return this.modelMapper.map(this.userRepository.findById(id), UserServiceDto.class);
    }

    @Override
    public Long register(UserRegisterDto userRegisterDto) {
        User newUser = this.modelMapper.map(userRegisterDto, User.class);
        this.userRepository.save(newUser);
        return newUser.getId();
    }

    @Override
    public UserServiceDto updateUser(Long id, UserServiceDto user) {
        User existingUser = userRepository.findById(id).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        this.userRepository.save(existingUser);
        return this.modelMapper.map(existingUser, UserServiceDto.class);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
