package com.talentgrid.app.user.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talentgrid.app.constants.ApplicationConstants;
import com.talentgrid.app.dto.UserDto;
import com.talentgrid.app.entity.Company;
import com.talentgrid.app.entity.Role;
import com.talentgrid.app.entity.TalentgridUser;
import com.talentgrid.app.repository.CompanyRepository;
import com.talentgrid.app.repository.RoleRepository;
import com.talentgrid.app.repository.TalentgridUserRepository;
import com.talentgrid.app.user.service.IUserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService{

    private final TalentgridUserRepository talentgridUserRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Optional<UserDto> searchUserByEmail(String email) {
        return talentgridUserRepository.findTalentgridUserByEmail(email)
                .map(this::mapToUserDto);
    }

    @Transactional
    @Override
    public UserDto elevateToEmployer(Long userId) {
        TalentgridUser user = talentgridUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if user is already an employer
        if (ApplicationConstants.ROLE_EMPLOYER.equals(user.getRole().getName())) {
            return mapToUserDto(user);
        }
        // Check if user is already an admin
        if (ApplicationConstants.ROLE_ADMIN.equals(user.getRole().getName())) {
            throw new RuntimeException("Cannot elevate admin user to employer role");
        }

        // Find ROLE_EMPLOYER
        Role employerRole = roleRepository.findRoleByName(ApplicationConstants.ROLE_EMPLOYER)
                .orElseThrow(() -> new RuntimeException("ROLE_EMPLOYER not found"));
        user.setRole(employerRole);
  
        /**
         * Why it’s unnecessary
         * findById() returns a managed entity
         * You modify it inside a transaction
         * Dirty checking automatically updates it
         */
        return mapToUserDto(user);
    }

    @Transactional
    @Override
    public UserDto assignCompanyToEmployer(Long userId, Long companyId) {
        TalentgridUser user = talentgridUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        // Verify user is an employer
        if (!ApplicationConstants.ROLE_EMPLOYER.equals(user.getRole().getName())) {
            throw new RuntimeException("User must be an employer to be assigned to a company");
        }
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));
        user.setCompany(company);
        // TalentgridUser updatedUser = userRepository.save(user);
        return mapToUserDto(user);
    }

    private UserDto mapToUserDto(TalentgridUser user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        dto.setUserId(user.getId());
        dto.setRole(user.getRole() != null ? user.getRole().getName() : null);
        dto.setCompanyId(user.getCompany() != null ? user.getCompany().getId() : null);
        dto.setCompanyName(user.getCompany() != null ? user.getCompany().getName() : null);
        return dto;
    }
}
