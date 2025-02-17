package com.example.Running.Club.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(
                    authorize -> authorize
                            .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/events").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/events/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/events/*/participants").hasRole("ADMIN")
                            .requestMatchers("/error/**").permitAll() // lets user see actual errors instead of 403 with no body
                            .anyRequest().authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(Customizer.withDefaults()); //apparently mandatory.
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
