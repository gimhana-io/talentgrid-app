package com.talentgrid.app.company.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentgrid.app.company.service.ICompanyService;
import com.talentgrid.app.dto.CompanyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final ICompanyService companyService;

  
    @GetMapping(path="/public", version = "1.0")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        log.trace("TRACE: this is a trace message");
        log.debug("DEBUG: this is a debug message");
        log.info("INFO: this is an info message");
        log.warn("WARN: this is a warning message");
        log.error("ERROR: this is an error message");
        List<CompanyDto> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
    
}
