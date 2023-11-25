package com.example.usermanagement.repo;

import com.example.usermanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailContainsIgnoreCase(String email);

    Optional<User> findByPhoneNumberContainsIgnoreCase(String phoneNumber);

    List<User> findAllByFirstNameContainsIgnoreCase(String firstName);

    List<User> findAllByLastNameContainsIgnoreCase(String lastName);

    List<User> findAllByBirthDate(Date date);
}
