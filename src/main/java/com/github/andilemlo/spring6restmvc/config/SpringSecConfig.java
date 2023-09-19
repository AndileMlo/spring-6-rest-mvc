package com.github.andilemlo.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                        .requestMatchers("/v3/api-docs**","/swagger-ui/**","/swagger-ui.html")
                        .permitAll()
                        .anyRequest().authenticated()
                        .and().oauth2ResourceServer().jwt();
     //   .csrf().ignoringRequestMatchers("/api/**");
        return http.build();

    }

}
