package com.talentgrid.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.TalentGridUser;

public interface TalentGridUserRepository extends JpaRepository<TalentGridUser, Long>{

    Optional<TalentGridUser> readUserByEmailOrMobileNumber(String email, String mobileNumber);
    
}
