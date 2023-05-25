// package com.chatop.backend.configuration;


// import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.SecurityFilterChain;

// import ch.qos.logback.core.subst.Token;

// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.authentication.AuthenticationManager;


// @Configuration
// public class SpringSecurityConfig {

//     @Bean 
//     public AuthenticationManager authentificationManagerBean() throws Exception {
//         return 

//     @Bean
//     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//         .authorizeHttpRequests(authorizeRequests -> 
//             authorizeRequests
//             .antMatchers("/admin").hasRole("ADMIN")
//             .antMatchers("/user").hasRole("USER")
//             .anyRequest().authenticated()
//         )
//         .formLogin(formLogin -> {});
//         //.oauth2Login(oauth2 -> {});

//     return http.build();
//     }
    
//     @Bean
//     UserDetailsService userDetailsService() {
//         InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//         manager.createUser(User.withUsername("springuser").password(passwordEncoder().encode("spring123")).roles("USER").build());
//         manager.createUser(User.withUsername("springadmin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER").build());
//         return manager;
//     }

//     @Bean
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }