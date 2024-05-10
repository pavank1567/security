package com.example.demo.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.*;

@Repository
public class ApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> findApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        "student",
                        passwordEncoder.encode("12345"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true


                ),
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("12345"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true


                ),
                new ApplicationUser(
                        "admint",
                        passwordEncoder.encode("12345"),
                        ADMIN_TRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true


                )
        );
     }
}
