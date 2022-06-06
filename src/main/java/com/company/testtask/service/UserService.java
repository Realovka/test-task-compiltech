package com.company.testtask.service;

import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    Long create(UserRequestDto userRequestDto);

    UserResponseDto findByLogin(String login);

    List<UserResponseDto> findAll();

    UserResponseDto update(Long id, UserRequestDto userRequestDto);

    void delete(Long id);
}
