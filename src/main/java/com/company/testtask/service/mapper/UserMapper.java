package com.company.testtask.service.mapper;

import com.company.testtask.dao.entity.User;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;

import java.util.List;

public interface UserMapper {

    User mapToEntity(UserRequestDto userRequestDto);

    UserResponseDto mapToDto(User user);

    List<UserResponseDto> mapToListDto(List<User> users);
}
