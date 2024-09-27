package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtil {

    //Only 1 and 4 is used (public)

    //1.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);//needs below 2 method
    }

    //2.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();//needs below 3 method
    }

    //3.
    private Key getSigningKey() {
        byte[] keyBytes= Decoders.BASE64.decode("413F4428472B4B62506553685660597033733676397924422645294840406351");
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //4.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);//needs below 5 method
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired (token);//needs below 6 method
    }

    //5.
    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);//needs below 8 method
    }

    //6.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());//needs below 7 method
    }

    //7.
    private Date extractExpiration (String token) {
        return extractClaim(token, Claims::getExpiration);//needs below 8 method
    }


    //8.
    private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token); //needs below 9 method
        return claimsResolvers.apply(claims);
    }

    //9.
    private Claims extractAllClaims (String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();//needs above 3 method
    }
}