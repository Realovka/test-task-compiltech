package com.company.testtask.service.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.dao.repository.UserRepository;
import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Long create(UserRequestDto userRequestDto) {
        User user = userMapper.mapToEntity(userRequestDto);
        return userRepository.save(user).getId();
    }
}
