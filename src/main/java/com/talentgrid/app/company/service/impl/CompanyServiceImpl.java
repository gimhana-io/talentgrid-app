package com.talentgrid.app.company.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talentgrid.app.company.service.ICompanyService;
import com.talentgrid.app.dto.CompanyDto;
import com.talentgrid.app.entity.Company;
import com.talentgrid.app.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(this::convertToDto).toList();
    }

    private CompanyDto convertToDto(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getLogo(),
                company.getIndustry(),
                company.getSize(),
                company.getRating(),
                company.getLocations(),
                company.getFounded(),
                company.getDescription(),
                company.getEmployees(),
                company.getWebsite(),
                company.getCreatedAt()
        );
    }

}
