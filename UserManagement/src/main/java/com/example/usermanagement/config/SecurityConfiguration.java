package com.example.usermanagement.config;

import com.example.usermanagement.model.enumeration.UserRoleEnum;
import com.example.usermanagement.repo.UserRepository;
import com.example.usermanagement.service.impl.UsersDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        //For testing and documentation purpose authentication level is set to Basic and CSRF is disabled!
        return httpSecurity.httpBasic().and().csrf().disable().authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/api/login", "/api/register").permitAll()
                        .requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/javainuse-openapi/**",
                                "/v3/api-docs/**",
                                "/v2/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/swagger-ui/*",
                                "/v3/**").permitAll()
                        .requestMatchers("/api/users/delete/**",
                                "/api/users/change-role/**").hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
        ).build();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UsersDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
