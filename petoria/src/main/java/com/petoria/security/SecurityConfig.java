package com.petoria.security;

import com.petoria.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthFilter tokenAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/signup", "/login").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/media/uploads/**", "/favicon.ico").permitAll()

                        .requestMatchers("/get-a-pet/**", "/pet/**", "/lost-and-found/**",
                                "/blog/**", "/notices/**", "/services", "/message/**", "/profile").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/users/signup", "/api/users/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/pets", "/api/services", "/api/notices", "/api/blog").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/pets/**", "/api/services/**", "/api/notices/**", "/api/blog/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pets/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/blog/*/comments").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/blog/*/comments").authenticated()

                        .anyRequest().authenticated()
                ) .build();
    }
}
