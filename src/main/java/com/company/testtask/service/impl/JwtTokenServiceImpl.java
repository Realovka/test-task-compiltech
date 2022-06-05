package com.company.testtask.service.impl;

import com.company.testtask.service.JwtTokenService;
import com.company.testtask.service.builder.JwtTokenBuilder;
import com.company.testtask.service.dto.JwtTokenDto;
import com.company.testtask.service.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenBuilder jwtTokenBuilder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtTokenDto create(UserAuthDto userAuthDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthDto.getLogin(),
                        userAuthDto.getPassword()));
        String jwt = jwtTokenBuilder.generateJwtToken(authentication);
        return new JwtTokenDto(jwt);
    }
}