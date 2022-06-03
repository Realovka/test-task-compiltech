package com.company.testtask.service.mapper.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.mapper.UserMapper;
import com.company.testtask.service.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapToEntity(UserRequestDto userRequestDto) {
        String password = passwordEncoder.createPasswordEncoded(userRequestDto.getPassword());
        return User.builder()
                .login(userRequestDto.getLogin())
                .password(password)
                .fullName(userRequestDto.getFullName())
                .build();
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .fullName(user.getFullName())
                .build();
    }

    @Override
    public List<UserResponseDto> mapToListDto(List<User> users) {
        return users.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
}
