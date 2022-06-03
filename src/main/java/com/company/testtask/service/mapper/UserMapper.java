package com.company.testtask.service.mapper;

import com.company.testtask.dao.entity.User;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;

public interface UserMapper {

    User mapToEntity(UserRequestDto userRequestDto);

    UserResponseDto mapToDto(User user);
}
