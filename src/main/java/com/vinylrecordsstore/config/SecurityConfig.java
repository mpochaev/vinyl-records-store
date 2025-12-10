package com.vinylrecordsstore.config;

import com.vinylrecordsstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/account/**", "/orders/**", "/order/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        // имена полей в форме логина
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        // как в project: key + срок жизни + свой параметр
                        .key("uniqueAndSecret")                 // в бою вынести в конфиг
                        .tokenValiditySeconds(86400 * 7)        // 7 дней
                        .rememberMeParameter("remember-me")     // name у checkbox в форме
                        .userDetailsService(userDetailsService())
                );

        return http.build();
    }

    /**
     * UserDetailsService – Spring Security будет через него искать пользователя по логину/почте.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // метод в UserService, который возвращает UserDetails по логину/почте
        return userService::getUserByLoginOrEmail;
    }
}