package com.company.testtask.service;

import com.company.testtask.service.dto.JwtTokenDto;
import com.company.testtask.service.dto.UserAuthDto;

public interface JwtTokenService {

    JwtTokenDto create(UserAuthDto userAuthDto);

}
