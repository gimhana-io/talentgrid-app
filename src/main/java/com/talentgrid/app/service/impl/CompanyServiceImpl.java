package com.talentgrid.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talentgrid.app.entity.Company;
import com.talentgrid.app.repository.CompanyRepository;
import com.talentgrid.app.service.ICompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

}
