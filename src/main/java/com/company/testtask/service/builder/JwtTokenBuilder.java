package com.company.testtask.service.builder;

import org.springframework.security.core.Authentication;

public interface JwtTokenBuilder {

    String generateJwtToken(Authentication authentication);

    boolean validateJwtToken(String jwt);

    String getUserNameFromJwtToken(String jwt);
}
