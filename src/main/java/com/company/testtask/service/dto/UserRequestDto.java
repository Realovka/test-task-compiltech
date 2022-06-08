package com.company.testtask.service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String login;
    private String password;
    private String fullName;
}
