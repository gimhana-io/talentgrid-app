package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
