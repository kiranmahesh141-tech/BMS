package com.example.bookstore.secutiy;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(authorize -> authorize
    .requestMatchers("/books/**").hasRole("ADMIN")
    .anyRequest().authenticated()
)
    .httpBasic(withDefaults());


        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password("{noop}admin123")
            .roles("ADMIN").build();
        UserDetails user = User.withUsername("user")
            .password("{noop}user123")
            .roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
