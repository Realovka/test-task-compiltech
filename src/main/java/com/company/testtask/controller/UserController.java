package com.company.testtask.controller;

import com.company.testtask.controller.socket.ServerWebSocketHandler;
import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserRequestDto;
import com.company.testtask.service.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ServerWebSocketHandler serverWebSocketHandler;

    @GetMapping()
    public List<UserResponseDto> findAll() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        serverWebSocketHandler.sendMessage(authentication);
        return userService.findAll();
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.update(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
