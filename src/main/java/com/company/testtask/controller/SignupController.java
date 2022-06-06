package com.company.testtask.controller;

import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody UserRequestDto userRequestDto) {
        return userService.create(userRequestDto);
    }
}
