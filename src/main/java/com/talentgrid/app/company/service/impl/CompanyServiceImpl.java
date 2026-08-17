package com.talentgrid.app.company.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talentgrid.app.company.service.ICompanyService;
import com.talentgrid.app.constants.ApplicationConstants;
import com.talentgrid.app.dto.CompanyDto;
import com.talentgrid.app.dto.JobDto;
import com.talentgrid.app.entity.Company;
import com.talentgrid.app.repository.CompanyRepository;
import com.talentgrid.app.util.ApplicationUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAllWithJobsByStatus(ApplicationConstants.ACTIVE_STATUS);
        return companies.stream().map(this::convertCompanyToDto).toList();
    }

    @Cacheable("companies")
    @Override
    public List<CompanyDto> getAllCompaniesForAdmin() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(this::convertCompanyToDtoForAdmin).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateCompanyDetails(Long id, CompanyDto companyDto) {
        int updatedRecords = companyRepository.updateCompanyDetails(
                id,companyDto.name(),companyDto.logo(),
                companyDto.industry(),companyDto.size(),companyDto.rating(),
                companyDto.locations(),companyDto.founded(),companyDto.description(),
                companyDto.employees(),companyDto.website()
        );
        return updatedRecords > 0;
    }

    @Transactional
    @Override
    public boolean createCompany(CompanyDto companyDto) {
        Company company = convertCompanyDtoToEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getId() != null && savedCompany.getId() > 0;
    }

    private CompanyDto convertCompanyToDto(Company company) {

        List<JobDto> jobDtos = company.getJobs().stream()
                .map(job -> ApplicationUtility.convertJobToDto(job))
                .collect(Collectors.toList());

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
                company.getCreatedAt(),
                jobDtos
                
        );
    }

    private Company convertCompanyDtoToEntity(CompanyDto companyDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDto, company);
        return company;
    }

    private CompanyDto convertCompanyToDtoForAdmin(Company company) {
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(),null);
    }


}
