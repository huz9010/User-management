package com.example.usermanagement.web;

import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.model.service.UserServiceDto;
import com.example.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
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
    public ResponseEntity<List<UserServiceDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/byFirstName/{firstName}")
    public ResponseEntity<List<UserServiceDto>> getUsersByFirstName(@PathVariable("firstName") String firstName) {
        return ResponseEntity.ok(this.userService.findByFirstName(firstName));
    }

    @GetMapping("/byLastName/{lastName}")
    public ResponseEntity<List<UserServiceDto>> getUsersByLastName(@PathVariable("lastName") String lastName) {
        return ResponseEntity.ok(this.userService.findByLastName(lastName));
    }

    @GetMapping("/byBirthDate/{birthDate}")
    public ResponseEntity<List<UserServiceDto>> getUsersByBirthDate(@PathVariable("birthDate") Date birthDate) {
        return ResponseEntity.ok(this.userService.findByBirthDate(birthDate));
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
        return ResponseEntity.ok(this.userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserServiceDto> deleteUser(@PathVariable Long id) {
        if (this.userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

