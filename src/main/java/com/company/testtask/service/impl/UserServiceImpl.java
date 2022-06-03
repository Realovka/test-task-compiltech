package com.company.testtask.service.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.dao.repository.UserRepository;
import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.exception.DuplicateEntityException;
import com.company.testtask.service.exception.EntityNotFoundException;
import com.company.testtask.service.mapper.UserMapper;
import com.company.testtask.service.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LogManager.getLogger();
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long create(UserRequestDto userRequestDto) {
        boolean result = userRepository.existsByLogin(userRequestDto.getLogin());
        if (!result) {
            User user = userMapper.mapToEntity(userRequestDto);
            return userRepository.save(user).getId();
        } else {
            logger.log(Level.ERROR, "Such login already exists");
            throw new DuplicateEntityException("Duplicate Entity", userRequestDto.getLogin());
        }
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.mapToListDto(users);
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such entity", id));
        updateUser(userRequestDto, user);
        User userFromDb = userRepository.save(user);
        return userMapper.mapToDto(userFromDb);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No such entity", id));
        userRepository.delete(user);
    }

    private void updateUser(UserRequestDto userRequestDto, User user) {
        if (userRequestDto.getLogin() != null) {
            user.setLogin(userRequestDto.getLogin());
        }
        if (userRequestDto.getPassword() != null) {
            String password = passwordEncoder.createPasswordEncoded(userRequestDto.getPassword());
            user.setPassword(password);
        }
        if (userRequestDto.getFullName() != null) {
            user.setFullName(user.getFullName());
        }
    }
}
