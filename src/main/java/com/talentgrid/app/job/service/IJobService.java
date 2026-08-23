package com.talentgrid.app.job.service;

import java.util.List;

import com.talentgrid.app.dto.JobApplicationDto;
import com.talentgrid.app.dto.JobDto;
import com.talentgrid.app.dto.UpdateJobApplicationDto;

public interface IJobService {

    List<JobDto> getEmployerJobs(String employerEmail);


    JobDto updateJobStatus(Long jobId, String status, String employerEmail);


    JobDto createJob(JobDto jobDto, String employerEmail);

    
    List<JobApplicationDto> getApplicationsByJobForEmployer(Long jobId);

    
    boolean updateJobApplication(UpdateJobApplicationDto updateJobApplicationDto);

}
