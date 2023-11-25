package com.example.usermanagement.model.dto;

import com.example.usermanagement.validation.UniqueEmail;
import com.example.usermanagement.validation.UniquePhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class UserRegisterDto {

    @NotNull
    @Size(min = 2, max = 60)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 60)
    private String lastName;
    @NotNull
    @PastOrPresent
    private Date birthDate;
    @NotNull
    @UniquePhoneNumber
    private String phoneNumber;
    @NotNull
    @Email
    @UniqueEmail
    private String email;

    public UserRegisterDto() {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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
}
