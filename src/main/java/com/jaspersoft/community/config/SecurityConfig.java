package com.jaspersoft.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.jaspersoft.community.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/forgot-password", "/forgot-username", "/reset-password/**", "/reset-username", "/css/**",  "/error", "/actuator/**").permitAll()
				
				 // Only allow ADMIN to access create/update/delete endpoints
                .requestMatchers("/qa/new", "/qa/update/**", "/qa/delete/**", "/wiki/new", "/wiki/update/**", "/wiki/delete/**").hasAnyRole("SUPERUSER", "MANAGEMENT_USER" )
				
                .requestMatchers("/manage/**").hasRole("MANAGEMENT_USER")
                .requestMatchers("/monitoring/**").hasAnyRole("USER","SUPERUSER", "MANAGEMENT_USER" )
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}