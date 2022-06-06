package com.company.testtask.controller;

import com.company.testtask.service.JwtTokenService;
import com.company.testtask.service.dto.JwtTokenDto;
import com.company.testtask.service.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenService jwtTokenService;

    @PostMapping()
    public JwtTokenDto login(@RequestBody UserAuthDto userAuthDto) {
        return jwtTokenService.create(userAuthDto);
    }
}