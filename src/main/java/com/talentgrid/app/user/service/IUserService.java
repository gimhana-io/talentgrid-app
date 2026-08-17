package com.talentgrid.app.user.service;

import java.util.Optional;

import com.talentgrid.app.dto.UserDto;

public interface IUserService {

    Optional<UserDto> searchUserByEmail(String email);

    
    UserDto elevateToEmployer(Long userId);

   
    UserDto assignCompanyToEmployer(Long userId, Long companyId);
}
