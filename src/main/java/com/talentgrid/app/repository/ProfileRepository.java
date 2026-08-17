package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}