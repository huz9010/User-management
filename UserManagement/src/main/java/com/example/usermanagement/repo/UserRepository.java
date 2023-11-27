package com.example.usermanagement.repo;

import com.example.usermanagement.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findByEmailContainsIgnoreCase(String email, Pageable pageable);

    Page<UserEntity> findByPhoneNumberContainsIgnoreCase(String phoneNumber, Pageable pageable);

    Page<UserEntity> findAllByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);

    Page<UserEntity> findAllByLastNameContainsIgnoreCase(String lastName, Pageable pageable);

    Page<UserEntity> findAllByBirthDate(LocalDate date, Pageable pageable);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Page<UserEntity> findAllByOrderByLastNameAscBirthDateAsc(Pageable pageable);

}
