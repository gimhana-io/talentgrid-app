package com.talentgrid.app.company.service;

import java.util.List;

import com.talentgrid.app.dto.CompanyDto;

public interface ICompanyService {
    
    List<CompanyDto> getAllCompanies();

    List<CompanyDto> getAllCompaniesForAdmin();

    void deleteCompanyById(Long id);

    boolean updateCompanyDetails(Long id, CompanyDto companyDto);

    boolean createCompany(CompanyDto companyDto);

}
