package com.company.testtask.controller;

import com.company.testtask.controller.socket.ServerWebSocketHandler;
import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureJsonTesters
@JsonTest
class UserControllerTest {

    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private ServerWebSocketHandler serverWebSocketHandler;
    private UserRequestDto userRequestDto;
    private UserResponseDto firstUserResponseDto;
    private UserResponseDto secondUserResponseDto;
    private UserResponseDto userResponseDto;
    private List<UserResponseDto> response;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .login("user")
                .build();
        firstUserResponseDto = UserResponseDto.builder()
                .id(1L)
                .login("user1")
                .fullName("user1")
                .build();
        secondUserResponseDto = UserResponseDto.builder()
                .id(2L)
                .login("user2")
                .fullName("user2")
                .build();
        userResponseDto = UserResponseDto.builder()
                .id(3L)
                .login("user")
                .fullName("user3")
                .build();
        response = List.of(firstUserResponseDto, secondUserResponseDto);
        userController = new UserController(userService, serverWebSocketHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }

    @Test
    void findAll() throws Exception {
        when(userController.findAll()).thenReturn(response);
        mockMvc.perform(get("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].login", equalTo("user1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void update() throws Exception {
        when(userController.update(eq(3L), eq(userRequestDto))).thenReturn(userResponseDto);
        mockMvc.perform(patch("/users/3")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userResponseDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteByIdRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/5")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}