package com.company.testtask.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDto {
    private String login;
    private String password;
    private String fullName;
}
