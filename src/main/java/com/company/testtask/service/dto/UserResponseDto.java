package com.company.testtask.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private Long id;
    private String login;
    private String fullName;
}
