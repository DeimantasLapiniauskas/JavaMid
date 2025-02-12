package com.example.thursday.security;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

//tells spring this has **__BEANS__, WHAT THE FUUUUUUUUCK**
@Configuration
//tells spring that this is a SecurityFilterChain
@EnableWebSecurity
public class SecurityConfig {

  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;

  @Bean
  //filterChain- kuriame savo custom filter
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(
                    authorize -> authorize // let who access things
                            .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/addMovie").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/updateMovie/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/deleteMovie/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/users").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/getMovies").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/getMovie/{id}").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/getMoviePaged").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/users").permitAll()
                            .anyRequest().authenticated()
            )
            .csrf(c -> c.disable()) //cross-site request forgery. disabled for convenience, do NOT do in reality
            .httpBasic(Customizer.withDefaults()) // basic auth
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
            .sessionManagement(
                    (session) -> session
                            .sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS
                            )
            )
            .exceptionHandling(
                    (exceptions) -> exceptions
                            .authenticationEntryPoint(
                                    new BearerTokenAuthenticationEntryPoint()
                            )
                            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );
//            .formLogin(Customizer.withDefaults()) // login page
//            .authorizeHttpRequests(authorize -> authorize // let who access things
//                    .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("USER")
//                    .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/addMovie").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.PUT, "/updateMovie/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/deleteMovie/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/users").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/getMovies").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/getMovie/{id}").hasRole("USER")
//                    .requestMatchers(HttpMethod.GET, "/getMoviePaged").hasRole("USER")
//                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
//
//                    .anyRequest().authenticated()
//            )


    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(key).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwks);
  }
}
