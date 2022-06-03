package com.company.testtask.service;

import com.company.testtask.service.dto.UserRequestDto;

public interface UserService {

    Long create(UserRequestDto userRequestDto);
}
