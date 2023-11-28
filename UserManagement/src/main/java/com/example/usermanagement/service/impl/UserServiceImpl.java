package com.example.usermanagement.service.impl;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.dto.UserUpdateDto;
import com.example.usermanagement.model.entity.UserEntity;
import com.example.usermanagement.model.dto.UserServiceDto;
import com.example.usermanagement.model.entity.UserRole;
import com.example.usermanagement.model.enumeration.UserRoleEnum;
import com.example.usermanagement.repo.UserRepository;
import com.example.usermanagement.repo.UserRoleRepository;
import com.example.usermanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserServiceDto> findByEmail(String email) {
        return Optional.ofNullable(this.modelMapper.map(this.userRepository.findByEmail(email), UserServiceDto.class));
    }

    @Override
    public Optional<UserServiceDto> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(this.modelMapper.map(this.userRepository.findByPhoneNumber(phoneNumber), UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findByEmail(String email, Pageable pageable) {
        return this.userRepository.findByEmailContainsIgnoreCase(email, pageable).
                map(u -> this.modelMapper.map(u, UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findByPhoneNumber(String phoneNumber, Pageable pageable) {
        return this.userRepository.findByPhoneNumberContainsIgnoreCase(phoneNumber, pageable).
                map(u -> this.modelMapper.map(u, UserServiceDto.class));
    }

    @Override
    public Page<UserServiceDto> findAll(Pageable pageable) {
        return this.userRepository.findAllByOrderByLastNameAscBirthDateAsc(pageable)
                .map(u -> this.modelMapper.map(u, UserServiceDto.class));
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
        Optional<UserEntity> user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceDto.class);
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {

        if(userExists(userRegisterDto)) {
            return null;
        }

        UserRole defaultRole;
        if (this.userRepository.count() == 0) {
            defaultRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        } else {
            defaultRole = this.userRoleRepository.findByRole(UserRoleEnum.USER);

        }
        UserEntity newUser = this.modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));
        newUser.setRole(defaultRole);
        this.userRepository.save(newUser);
        return newUser.getEmail();
    }

    @Override
    public boolean updateUser(Long id, UserUpdateDto userUpdateDto) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        UserEntity userToSave;
        if (existingUser.isPresent()) {
            if (userExists(userUpdateDto)) {
                return false;
            }
                userToSave = existingUser.get();
                userToSave.setFirstName(userUpdateDto.getFirstName());
                userToSave.setLastName(userUpdateDto.getLastName());
                userToSave.setBirthDate(userUpdateDto.getBirthDate());
                userToSave.setPhoneNumber(userUpdateDto.getPhoneNumber());

                this.userRepository.save(userToSave);
                return true;
            }
            return false;
        }

    @Override
    public boolean deleteUser(Long id) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        Optional<UserEntity> user = userRepository.findById(userId);
        return user.map(u -> u.getEmail().equals(currentEmail)).orElse(false);
    }

    @Override
    public <U> boolean userExists(U user) {
        UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
        Optional<UserEntity> mailCheck = this.userRepository.findByEmail(userEntity.getEmail());
        Optional<UserEntity> phoneCheck = this.userRepository.findByPhoneNumber(userEntity.getPhoneNumber());
        return  mailCheck.isPresent() || phoneCheck.isPresent();
    }

    @Override
    public boolean changeUserRole(Long id, String role) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            UserRole userRole = this.userRoleRepository.findByRole(UserRoleEnum.valueOf(role.toUpperCase()));
            user.get().setRole(userRole);
            this.userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
