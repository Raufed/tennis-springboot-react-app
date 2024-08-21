package by.payyzau.tennis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecutiryConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"api/v1/tennismenu").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/v1/menu").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"api/v1/menu/image").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"api/v1/menu/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/v1/menu/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/auth/singUp").permitAll()
                        .requestMatchers("api/v1/auth/singIn").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
