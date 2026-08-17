package com.talentgrid.app.job.service;

import java.util.List;

import com.talentgrid.app.dto.JobDto;

public interface IJobService {

    List<JobDto> getEmployerJobs(String employerEmail);


    JobDto updateJobStatus(Long jobId, String status, String employerEmail);


    JobDto createJob(JobDto jobDto, String employerEmail);

}
