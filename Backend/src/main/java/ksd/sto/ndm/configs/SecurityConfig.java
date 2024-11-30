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

import ksd.sto.ndm.cmns.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin", "/api/**")
                .authenticated()
                .anyRequest()
                .permitAll())
            .formLogin(
                    req -> req.loginPage("/login_admin.html").loginProcessingUrl("/inter-process")
            // .usernameParameter("userId")
            // .passwordParameter("password")
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 계층구조에서 권한을 구분할 때 줄바꿈으로 분리.
     * @return
     */
    @Bean
    RoleHierarchy roleHierachy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_NP_MANAGE\nROLE_NA_MANAGER\nROLE_SYS_MANAGER > ROLE_USER");
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