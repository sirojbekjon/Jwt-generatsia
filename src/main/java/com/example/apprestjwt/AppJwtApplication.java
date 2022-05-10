package com.example.apprestjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppJwtApplication {

    @Bean
    PasswordEncoder passwordEncoder(){
//        BCryptPasswordEncoder bCryptPasswordEncoder;
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppJwtApplication.class, args);
    }

}
