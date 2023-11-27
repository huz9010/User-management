package com.example.usermanagement.model.dto;

import com.example.usermanagement.validation.FieldMatch;
import com.example.usermanagement.validation.UniqueEmail;
import com.example.usermanagement.validation.UniquePhoneNumber;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords should match."
)

public class UserUpdateDto {

    @NotNull
    @Size(min = 2, max = 60)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 60)
    private String lastName;
    @NotNull
    @PastOrPresent
    private LocalDate birthDate;
    @NotNull
    @UniquePhoneNumber
    @Pattern(regexp = "^[+]?[0-9]+")
    private String phoneNumber;
    @NotNull
    @Email
    @UniqueEmail
    private String email;
    @NotNull
    @Size(min = 6, max = 20)
    private String password;
    @NotNull
    @Size(min = 6, max = 20)
    private String confirmPassword;


    public UserUpdateDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
