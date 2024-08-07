package com.sbz.appa.config;

import com.sbz.appa.config.filter.JwtTokenGeneratorFilter;
import com.sbz.appa.config.filter.JwtTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> { // CORS Configuration
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("*")); // All origins
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(List.of("Authorization")); // Expose Authorization header
                config.setMaxAge(3600L);
                return config;
            }))
            .csrf(AbstractHttpConfigurer::disable) // CSRF Configuration
            .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> requests // Permissions Configuration
                    .requestMatchers(
                            "/v1/users/register/citizen"
                    ).permitAll()
                    .requestMatchers(
                            "/v1/users/login",
                            "/v1/users/update"
                    ).authenticated()
                    .requestMatchers(
                            "/v1/roles/create",
                            "/v1/roles/list",
                            "v1/users/register/staff",
                            "v1/users/role/{role}"
                    ).hasRole("ADMIN")
                    .requestMatchers(
                            "/v1/users/delete/{id}"
                    ).hasAnyRole("ADMIN", "CITIZEN")
                    .requestMatchers(
                            "/v1/services/create",
                            "/v1/services/get/price",
                            "/v1/users/get/services/last-service",
                            "v1/services/track/{guideId}"
                    ).hasRole("CITIZEN")
                    .requestMatchers(
                            "/v1/services/get/{id}",
                            "/v1/users/get/services",
                            "/v1/services/get/route"
                    ).hasAnyRole("CITIZEN", "BISON")
                    .requestMatchers(
                            "/v1/services/update/{id}",
                            "/v1/users/get/services/active"
                    ).hasRole("BISON")
            )
            .formLogin(withDefaults())
            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
