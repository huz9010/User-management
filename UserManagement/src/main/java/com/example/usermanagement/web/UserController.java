package com.example.usermanagement.web;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.service.UserServiceDto;
import com.example.usermanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserServiceDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byFirstName/{firstName}")
    public ResponseEntity<List<UserServiceDto>> getUsersByFirstName(
            @PathVariable("firstName") String firstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findByFirstName(firstName, PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byLastName/{lastName}")
    public ResponseEntity<List<UserServiceDto>> getUsersByLastName(
            @PathVariable("lastName") String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findByLastName(lastName, PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byBirthDate/{birthDate}")
    public ResponseEntity<List<UserServiceDto>> getUsersByBirthDate(
            @PathVariable("birthDate") LocalDate birthDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findByBirthDate(birthDate, PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byPhoneNumber/{phoneNumber}")
    public ResponseEntity<UserServiceDto> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        Optional<UserServiceDto> optionalUser = this.userService.findByPhoneNumber(phoneNumber);
        return optionalUser
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto user, UriComponentsBuilder uriComponentsBuilder) {
        Long newUserId = this.userService.register(user);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/users/{id}").build(newUserId)
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserServiceDto> updateUser(@PathVariable Long id, @RequestBody UserServiceDto user) {
        if (this.userService.updateUser(id, user)) {
            return ResponseEntity.ok(this.userService.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserServiceDto> deleteUser(@PathVariable Long id) {
        if (this.userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

