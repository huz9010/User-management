package com.example.usermanagement.web;

import com.example.usermanagement.model.dto.UserServiceDto;
import com.example.usermanagement.model.dto.UserUpdateDto;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
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

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<List<UserServiceDto>> getUserByEmail(
            @PathVariable("email") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findByEmail(email, PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byPhoneNumber/{phoneNumber}")
    public ResponseEntity<List<UserServiceDto>> getUserByPhoneNumber(
            @PathVariable("phoneNumber") String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserServiceDto> usersPage = this.userService.findByPhoneNumber(phoneNumber, PageRequest.of(page, size));
        return ResponseEntity.ok(usersPage.getContent());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserServiceDto> getUserById(
            @PathVariable("id") Long id) {
        UserServiceDto user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserServiceDto> deleteUser(@PathVariable Long id) {
        if (id == 1) {
            return ResponseEntity.badRequest().build();
        }
        if (this.userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isCurrentUser(#id)")
    public ResponseEntity<UserServiceDto> updateUser(@PathVariable Long id,
                                                      @RequestBody @Valid UserUpdateDto user,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        if (this.userService.updateUser(id, user)) {
            return ResponseEntity.ok(this.userService.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/change-role/{id}")
    public ResponseEntity<HttpStatus> changeUserRole(@PathVariable Long id,
                                            @RequestBody String role) {
        if (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("USER")) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        if (!this.userService.changeUserRole(id, role)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

