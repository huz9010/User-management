package com.example.usermanagement.service.impl;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.entity.User;
import com.example.usermanagement.model.service.UserServiceDto;
import com.example.usermanagement.repo.UserRepository;
import com.example.usermanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

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
    public Page<UserServiceDto> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable).
                map(u -> this.modelMapper.map(u, UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findByFirstName(String firstName, Pageable pageable) {
        return this.userRepository.findAllByFirstNameContainsIgnoreCase(firstName, pageable)
                .map(u -> this.modelMapper.map(u, UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findByLastName(String lastName, Pageable pageable) {
        return this.userRepository.findAllByLastNameContainsIgnoreCase(lastName, pageable)
                .map(u -> this.modelMapper.map(u, UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findByBirthDate(LocalDate birthDate, Pageable pageable) {
        return this.userRepository.findAllByBirthDate(birthDate, pageable)
                .map(u -> this.modelMapper.map(u, UserServiceDto.class));
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
    public boolean updateUser(Long id, UserServiceDto user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            existingUser.get().setFirstName(user.getFirstName());
            existingUser.get().setLastName(user.getLastName());
            existingUser.get().setBirthDate(user.getBirthDate());
            existingUser.get().setPhoneNumber(user.getPhoneNumber());

            this.userRepository.save(existingUser.get());
            return true;
        }
        return false;
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
