package ksd.sto.ndm.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ksd.sto.ndm.cmns.exceptions.AccessDeniedHandlerImpl;
import ksd.sto.ndm.cmns.exceptions.AuthenticationEntryPointImpl;
import ksd.sto.ndm.cmns.filters.LoggingFilter;
import ksd.sto.ndm.cmns.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final LoggingFilter loggingFilter;
    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .formLogin(
                    // req ->
                    // req.loginPage("/login").loginProcessingUrl("/inter-process")
                    // req -> req.loginPage("/login").permitAll()
                    req -> req.disable()
            // .usernameParameter("userId")
            // .passwordParameter("password")
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//            .addFilterAfter(loggingFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> {
                ex.authenticationEntryPoint(authenticationEntryPointImpl);
//                ex.accessDeniedHandler(accessDeniedHandlerImpl);
            });

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 계층구조에서 권한을 구분할 때 줄바꿈으로 분리.
     * 
     * @return
     */
    @Bean
    RoleHierarchy roleHierachy() {
        return RoleHierarchyImpl
            .fromHierarchy("ROLE_ADMIN > ROLE_NP_MANAGE > ROLE_USER > ROLE_ANONYMOUS");
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