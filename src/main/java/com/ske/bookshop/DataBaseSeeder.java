package com.ske.bookshop;


import com.ske.bookshop.securityJwt.controllers.AuthController;
import com.ske.bookshop.securityJwt.models.ERole;
import com.ske.bookshop.securityJwt.models.Role;
import com.ske.bookshop.securityJwt.payload.request.SignupRequest;
import com.ske.bookshop.securityJwt.repository.RoleRepository;
import com.ske.bookshop.securityJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
@Slf4j
public class DataBaseSeeder {

    RoleRepository roleRepository;
    AuthController authController;
    UserRepository userRepository;

    void seedRoles(){
        Arrays.stream(ERole.values()).forEach(eRole ->
                roleRepository.findByName(eRole)
                        .ifPresentOrElse(role -> {log.info("Role: " + eRole.toString() + " already exist.");
                            }, () -> roleRepository.save(new Role(eRole))));
    }
    void seedExampleUsers(){
        SignupRequest signupRequest = new SignupRequest("admin", "admin@gmail.com", "admin");
        authController.registerUser(signupRequest);
        userRepository.findByUsername("admin").ifPresent(u ->{
            u.getRoles().add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
            userRepository.save(u);
        });

    }
    @Bean
    CommandLineRunner seeder(){
        return args -> {
            seedRoles();
            seedExampleUsers();
        };
    }
}
