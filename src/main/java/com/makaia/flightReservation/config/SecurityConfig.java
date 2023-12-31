package com.makaia.flightReservation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                // Permitir acceso sin autenticación a rutas específicas
                                .antMatchers(
                                        "/v3/api-docs/**", "/configuration/**", "/swagger-ui/**", "/webjars/**", "/auth/**").permitAll()
                                // Restricciones específicas para rutas
                                .antMatchers("/users", "/roles").hasAuthority("ADMIN")
                                .antMatchers(HttpMethod.GET, "/reservations/{reservationCode}", "/reservations/passenger/{passengerId}", "/flights/**").hasAnyAuthority("USER", "AIRLINE", "ADMIN")
                                .antMatchers(HttpMethod.GET, "/reservations").hasAnyAuthority("ADMIN", "AIRLINE")
                                .antMatchers(HttpMethod.POST, "/reservations/**").hasAnyAuthority("ADMIN", "AIRLINE", "USER")
                                // Restricciones específicas por método HTTP
                                .antMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                                .antMatchers(HttpMethod.PUT).hasAnyAuthority("ADMIN", "AIRLINE")
                                .antMatchers(HttpMethod.POST).hasAnyAuthority("ADMIN", "AIRLINE")
                                .antMatchers(HttpMethod.GET).hasAnyAuthority("ADMIN", "AIRLINE")
                                // Todas las demás peticiones requieren autenticación
                                .anyRequest().authenticated()
                )

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
