package com.example.apprestjwt.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;


@Component
public class JwtProvider {


    static long expireTime=36_000_000;
    static String secretKey="BuMeningSecretSozimHechKimBilmasinSiroj1294QoshimchaqilibAytgandaYetarliEmas23232323232";
    public static String generateToken(String username){
        Date expireDate = new Date(System.currentTimeMillis()+expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    public static void main(String[] args) {
        String token = generateToken("userLogini");
        System.out.println(token);
    }

    public boolean validateToken(String token){
            try {
        Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return true;
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
    }

    public String getUsernameFromToken(String token){
        String username = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
