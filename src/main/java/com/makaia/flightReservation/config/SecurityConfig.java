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
                                .antMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**", "/webjars/**", "/auth/**").permitAll()
                                // Restricciones específicas por método HTTP
                                .antMatchers(HttpMethod.DELETE).hasAuthority("ADMIN")
                                .antMatchers(HttpMethod.PUT).hasAnyAuthority("ADMIN", "AIRLINE")
                                .antMatchers(HttpMethod.POST).hasAnyAuthority("ADMIN", "AIRLINE")
                                // Restricciones específicas para rutas
                                .antMatchers("/users", "/roles").hasAuthority("ADMIN")
                                .antMatchers(HttpMethod.GET, "/flights/**").permitAll() // Todos pueden acceder
                                .antMatchers(HttpMethod.GET, "/api/reservations/{reservationCode}", "/api/reservations/passenger/{passengerId}").hasAnyAuthority("USER")
                                .antMatchers(HttpMethod.GET, "/api/reservations").hasAnyAuthority("ADMIN", "AIRLINE")
                                .antMatchers(HttpMethod.POST, "/api/reservations/**").hasAnyAuthority("ADMIN", "AIRLINE", "USER")
                                // Todas las demás peticiones requieren autenticación
                                .anyRequest().permitAll()
                )

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
