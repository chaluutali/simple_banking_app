package com.chaluutali.chirwa.simple_banking_app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    //public PasswordEncoder passwordEncoder(){
    //    return new BCryptPasswordEncoder(10);
   // }
}
