package com.example.apprestjwt.security;

import com.example.apprestjwt.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MimeHeaders;
import java.io.IOException;

@Component
public class JwtFelter extends OncePerRequestFilter {

    @Autowired
    MyAuthService myAuthService;
    @Autowired
    JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //REQUESTDAN TOKENNI OLISH
        String token = httpServletRequest.getHeader("Authorization");

        //TOKEN BORLIGINI VA TOKENNING BOSHLANISHI BEARER BO'LISHINI TEKSHIRYAPMIZ
        if (token!=null && token.startsWith("Bearer")){

            //AYNAN TOKENNI O'ZINI QIRQIB OLDIK
            token = token.substring(7);

            //TOKENNI VALIDATSIADAN O'TQAZDIK (TOKEN BUZILMAGANLIGINI MUDDATI O'TMAGANLIGINI VA H.K)
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken){

                //TOKENNI ICHIDAN USERNAME NI OLDIK
                String usernameFromToken = jwtProvider.getUsernameFromToken(token);

                //USERNAME ORQALI USERDETAILSNI OLDIK
                UserDetails userDetails = myAuthService.loadUserByUsername(usernameFromToken);

                //USERDETAILS ORQALI AUTHENTICATION YARATIB OLDIK
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                System.out.println(SecurityContextHolder.getContext().getAuthentication());

                //SESTEMGA KIM KIRGANLIGINI O'RNATIB QOYDIK
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
