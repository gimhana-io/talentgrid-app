package com.talentgrid.app.job.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talentgrid.app.dto.JobApplicationDto;
import com.talentgrid.app.dto.JobDto;
import com.talentgrid.app.dto.UpdateJobApplicationDto;
import com.talentgrid.app.entity.Job;
import com.talentgrid.app.entity.JobApplication;
import com.talentgrid.app.entity.TalentgridUser;
import com.talentgrid.app.job.service.IJobService;
import com.talentgrid.app.repository.JobApplicationRepository;
import com.talentgrid.app.repository.JobRepository;
import com.talentgrid.app.repository.TalentgridUserRepository;
import com.talentgrid.app.util.ApplicationUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobServiceImpl implements IJobService {

    private final JobRepository jobRepository;
    private final TalentgridUserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public List<JobDto> getEmployerJobs(String employerEmail) {
        TalentgridUser employer = userRepository.findTalentgridUserByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer does not have a company assigned");
        }

        List<Job> jobs = employer.getCompany().getJobs();
        return jobs.stream()
                .map(job -> ApplicationUtility.convertJobToDto(job))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public JobDto updateJobStatus(Long jobId, String status, String employerEmail) {
       
        if (!status.equals("ACTIVE") && !status.equals("CLOSED") && !status.equals("DRAFT")) {
            throw new RuntimeException("Invalid status. Must be ACTIVE, CLOSED, or DRAFT");
        }
        TalentgridUser employer = userRepository.findTalentgridUserByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer does not have a company assigned");
        }
        Job job = employer.getCompany().getJobs().stream().filter(j -> j.getId().equals(jobId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(status);
        return ApplicationUtility.convertJobToDto(job);
    }

    @Override
    @Transactional
    public JobDto createJob(JobDto jobDto, String employerEmail) {
       
        TalentgridUser employer = userRepository.findTalentgridUserByEmail(employerEmail)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer does not have a company assigned. Please contact admin.");
        }
        Job job = tranformDtoToEntity(jobDto);
        job.setPostedDate(Instant.now());
        job.setApplicationsCount(0);
        job.setStatus("DRAFT");
        job.setCompany(employer.getCompany());
        Job savedJob = jobRepository.save(job);
        return ApplicationUtility.convertJobToDto(savedJob);
    }

    @Override
    public List<JobApplicationDto> getApplicationsByJobForEmployer(Long jobId) {
        List<JobApplication> applications = jobApplicationRepository.findByJobIdOrderByAppliedAtAsc(jobId);
        return applications.stream()
                .map(jobApplication -> ApplicationUtility.mapToJobApplicationDto(jobApplication))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean updateJobApplication(UpdateJobApplicationDto dto) {
        int updatedRows = jobApplicationRepository.updateStatusAndNotesById(
                dto.status().name(), dto.notes(),dto.applicationId(), ApplicationUtility.getLoggedInUser());
        return updatedRows > 0;
    }

    private Job tranformDtoToEntity(JobDto jobDto) {
        Job job = new Job();
        BeanUtils.copyProperties(jobDto, job);
        return job;
    }
}
