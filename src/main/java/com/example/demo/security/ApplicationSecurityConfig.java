package com.example.demo.security;

import com.example.demo.auth.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.demo.security.ApplicationUserPermission.STUDENT_WRITE;
import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig{

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf((csrf)-> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers("/api/v1/students/**").hasRole(STUDENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/manage/**").hasAuthority(STUDENT_WRITE.getPermission())
                        .requestMatchers(HttpMethod.GET,"/api/manage/**").hasAnyRole(ApplicationUserRole.ADMIN_TRAINEE.name(), ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
//                .httpBasic(withDefaults());
                .formLogin(formlogin -> formlogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/courses")
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer.rememberMeParameter("remember-me"))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


  /*  @Bean
    protected InMemoryUserDetailsManager userDetailsService(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails stu = User.builder()
                .username("student1")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails adminTrainee = User.builder()
                .username("admint")
                .password(passwordEncoder.encode("admint"))
//                .roles(ADMIN_TRAINEE.name())
                .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                stu,
                adminTrainee
        );

    }*/
}
