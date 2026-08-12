package com.talentgrid.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.TalentgridUser;

public interface TalentgridUserRepository extends JpaRepository<TalentgridUser, Long>{

    Optional<TalentgridUser> readUserByEmailOrMobileNumber(String email, String mobileNumber);

    Optional<TalentgridUser> findTalentgridUserByEmail(String email);
    
}
