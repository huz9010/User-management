package com.example.usermanagement.web;

import com.example.usermanagement.model.dto.UserLoginDto;
import com.example.usermanagement.model.dto.UserRegisterDto;
import com.example.usermanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final HttpSession  httpSession;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, HttpSession httpSession) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successful!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid UserRegisterDto user,
            BindingResult bindingResult) {
        String newUserEmail = this.userService.register(user);
        if (newUserEmail == null) {
            return new ResponseEntity<>("User already exists!", HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        return new ResponseEntity<>(newUserEmail, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        this.httpSession.invalidate();
        return ResponseEntity.ok().build();
    }
}
