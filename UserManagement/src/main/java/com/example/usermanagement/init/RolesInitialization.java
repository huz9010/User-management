package com.example.usermanagement.init;

import com.example.usermanagement.model.entity.UserRole;
import com.example.usermanagement.model.enumeration.UserRoleEnum;
import com.example.usermanagement.repo.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class RolesInitialization implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public RolesInitialization(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.userRoleRepository.count() == 0) {
            this.userRoleRepository.saveAll(Arrays.stream(UserRoleEnum.values()).map(UserRole::new).toList());
        }
    }
}
