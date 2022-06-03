package com.company.testtask.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String login;
    private String fullName;
}
