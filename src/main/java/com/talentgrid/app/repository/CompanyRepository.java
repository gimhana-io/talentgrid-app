package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
