package com.company.testtask.service.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.dao.repository.UserRepository;
import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.exception.DuplicateEntityException;
import com.company.testtask.service.exception.EntityNotFoundException;
import com.company.testtask.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.company.testtask.service.exception.ExceptionMessage.DUPLICATE_ENTITY;
import static com.company.testtask.service.exception.ExceptionMessage.NO_SUCH_ENTITIES;
import static com.company.testtask.service.exception.ExceptionMessage.NO_SUCH_ENTITY;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public Long create(UserRequestDto userRequestDto) {
        boolean result = userRepository.existsByLogin(userRequestDto.getLogin());
        if (!result) {
            User user = userMapper.mapToEntity(userRequestDto);
            return userRepository.save(user).getId();
        } else {
            log.error("Such login already exists: {}", userRequestDto.getLogin());
            throw new DuplicateEntityException(DUPLICATE_ENTITY, userRequestDto.getLogin());
        }
    }

    @Override
    public UserResponseDto findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_ENTITY, login));
        return userMapper.mapToDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.mapToListDto(users);
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_ENTITY, id));
        updateUser(userRequestDto, user);
        User userFromDb = userRepository.save(user);
        return userMapper.mapToDto(userFromDb);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_ENTITY, id));
        userRepository.delete(user);
    }

    @Override
    public void deleteByIdRange(Long startId, Long finishId) {
        List<User> users = userRepository.findByIdRange(startId, finishId);
        if (users.size() < 1) {
            throw new EntityNotFoundException(NO_SUCH_ENTITIES, List.of(startId, finishId));
        }
        userRepository.deleteByIdRange(startId, finishId);
    }

    private void updateUser(UserRequestDto userRequestDto, User user) {
        if (userRequestDto.getLogin() != null) {
            boolean result = userRepository.existsByLogin(userRequestDto.getLogin());
            if (result) {
                throw new DuplicateEntityException(DUPLICATE_ENTITY, userRequestDto.getLogin());
            }
            user.setLogin(userRequestDto.getLogin());
        }
        if (userRequestDto.getPassword() != null) {
            String password = new BCryptPasswordEncoder().encode(userRequestDto.getPassword());
            user.setPassword(password);
        }
        if (userRequestDto.getFullName() != null) {
            user.setFullName(userRequestDto.getFullName());
        }
    }
}
