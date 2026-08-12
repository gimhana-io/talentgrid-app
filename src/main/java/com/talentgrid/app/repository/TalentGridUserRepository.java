package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.TalentGridUser;

public interface TalentGridUserRepository extends JpaRepository<TalentGridUser, Long>{
    
}
