package ksd.sto.ndm.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ksd.sto.ndm.cmns.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**","/admin")
                .authenticated()
                // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                // "/swagger-resources/**", "/swagger-ui.html"
                // "/webjars/**",
                // "/configuration/**"
                // )
                // .hasRole("ADMIN")
                // .permitAll()
                .anyRequest()
                .permitAll())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(login -> login
                .usernameParameter("userId")
                .passwordParameter("password")
                .loginProcessingUrl("/login2")
                .loginPage("/admin")
             );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // AuthenticationManager authenticationManager(UserDetailsService
    // userDetailsService,
    // PasswordEncoder passwordEncoder) {
    // DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    // authProvider.setUserDetailsService(userDetailsService);
    // authProvider.setPasswordEncoder(passwordEncoder);
    // return new ProviderManager(authProvider);
    // }
}