package com.example.thursday.security;


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

//tells spring this has **__BEANS__, WHAT THE FUUUUUUUUCK**
@Configuration
//tells spring that this is a SecurityFilterChain
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  //filterChain- kuriame savo custom filter
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable) //cross-site request forgery. disabled for convenience, do NOT do in reality
            .httpBasic(Customizer.withDefaults()) // basic auth
            .formLogin(Customizer.withDefaults()) // login page
            .authorizeHttpRequests(authorize -> authorize // let who access things
                    .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyRequest().authenticated()
            );

    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
