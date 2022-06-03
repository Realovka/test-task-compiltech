package com.company.testtask.service.mapper.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .login(userRequestDto.getLogin())
                .password(userRequestDto.getPassword())
                .fullName(userRequestDto.getFullName())
                .build();
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .build();
    }
}
