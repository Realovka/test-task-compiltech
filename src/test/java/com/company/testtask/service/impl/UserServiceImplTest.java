package com.company.testtask.service.impl;

import com.company.testtask.dao.entity.User;
import com.company.testtask.dao.repository.UserRepository;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.exception.DuplicateEntityException;
import com.company.testtask.service.exception.EntityNotFoundException;
import com.company.testtask.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    private UserRequestDto userRequestDto;
    private User user;
    private User userDB;
    private User updateUserDB;
    private User secondUserDB;
    private List<User> users;
    private UserResponseDto userResponseDto;
    private UserResponseDto updateUserResponseDto;
    private UserResponseDto secondUserResponseDto;
    private List<UserResponseDto> responseDtoList;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userMapper, userRepository);
        userRequestDto = new UserRequestDto("user", "user", "user");
        user = User.builder()
                .login("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .fullName("user")
                .build();
        userDB = User.builder()
                .id(1L)
                .login("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .fullName("user")
                .build();
        updateUserDB = User.builder()
                .id(1L)
                .login("user")
                .password(new BCryptPasswordEncoder().encode("user"))
                .fullName("newFullName")
                .build();
        secondUserDB = User.builder()
                .id(2L)
                .login("user2")
                .password(new BCryptPasswordEncoder().encode("user2"))
                .fullName("user2")
                .build();
        users = List.of(userDB, secondUserDB);
        userResponseDto = UserResponseDto.builder()
                .id(1L)
                .login("user")
                .fullName("user")
                .build();
        updateUserResponseDto = UserResponseDto.builder()
                .id(1L)
                .login("user")
                .fullName("newFullName")
                .build();
        secondUserResponseDto = UserResponseDto.builder()
                .id(2L)
                .login("user2")
                .fullName("user2")
                .build();
        responseDtoList = List.of(userResponseDto, secondUserResponseDto);
    }

    @Test
    void createTest() {
        when(userRepository.existsByLogin(userRequestDto.getLogin())).thenReturn(false);
        when(userMapper.mapToEntity(userRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(userDB);
        Long actual = userService.create(userRequestDto);
        assertEquals(1L, actual);
    }

    @Test
    public void createThrowsDuplicateEntityExceptionTest() {
        when(userRepository.existsByLogin(userRequestDto.getLogin())).thenReturn(true);
        assertThrows(DuplicateEntityException.class, () -> userService.create(userRequestDto));
    }

    @Test
    void findByLoginTest() {
        when(userRepository.findByLogin("login")).thenReturn(Optional.ofNullable(userDB));
        when(userMapper.mapToDto(userDB)).thenReturn(userResponseDto);
        UserResponseDto actual = userService.findByLogin("login");
        assertEquals(userResponseDto, actual);
    }

    @Test
    public void findByLoginThrowsEntityNotFoundExceptionTest() {
        when(userRepository.findByLogin("login")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByLogin("login"));
    }

    @Test
    void findAllTest() {
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.mapToListDto(users)).thenReturn(responseDtoList);
        List<UserResponseDto> actual = userService.findAll();
        assertEquals(responseDtoList, actual);
    }

    @Test
    void updateTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userDB));
        userDB.setFullName("newFullName");
        when(userRepository.save(userDB)).thenReturn(updateUserDB);
        when(userMapper.mapToDto(updateUserDB)).thenReturn(updateUserResponseDto);
        UserResponseDto actual = userService.update(1L, userRequestDto);
        assertEquals(updateUserResponseDto, actual);
    }

    @Test
    public void updateThrowsEntityNotFoundExceptionTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(1L, userRequestDto));
    }

    @Test
    void deleteTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userDB));
        userService.delete(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteThrowsEntityNotFoundExceptionTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void deleteByIdRangeTest() {
        when(userRepository.findByIdRange(1L, 2L)).thenReturn(users);
        userService.deleteByIdRange(1L, 2L);
        verify(userRepository,  times(1)).deleteByIdRange(1L, 2L);
    }

    @Test
    void deleteByIdRangeThrowsEntityNotFoundExceptionTest() {
        when(userRepository.findByIdRange(10L, 20L)).thenReturn(List.of());
        assertThrows(EntityNotFoundException.class, () -> userService.deleteByIdRange(10L, 20L));
    }
}