package com.example.usermanagement.repo;

import com.example.usermanagement.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailContainsIgnoreCase(String email);

    Optional<User> findByPhoneNumberContainsIgnoreCase(String phoneNumber);

    Page<User> findAllByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);

    Page<User> findAllByLastNameContainsIgnoreCase(String lastName, Pageable pageable);

    Page<User> findAllByBirthDate(LocalDate date, Pageable pageable);
}
