package com.example.apprestjwt.config;

import com.example.apprestjwt.security.JwtFelter;
import com.example.apprestjwt.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtFelter jwtFelter;

    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(myAuthService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/","/api/auth/login").permitAll()
                .anyRequest().authenticated();

        //SPRING SECURITY GA "UsernamePasswordAuthenticationFilter.class" dan OLDIN jwtFelter NI ISHLASHINI AYTMOQDA
        http.addFilterBefore(jwtFelter, UsernamePasswordAuthenticationFilter.class);

        //SPRING SECURETYGA SESSIONGA USHLAB QOLMASLIGINI BUYURMOQDA
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
