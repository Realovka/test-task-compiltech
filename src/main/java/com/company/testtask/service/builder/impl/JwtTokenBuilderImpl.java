package com.company.testtask.service.builder.impl;

import com.company.testtask.service.builder.JwtTokenBuilder;
import com.company.testtask.service.security.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenBuilderImpl implements JwtTokenBuilder {

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private long jwtExpirationMs;

    @Override
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    @Override
    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JwtToken expired: {}", jwt);
        }
        return false;
    }

    @Override
    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }
}