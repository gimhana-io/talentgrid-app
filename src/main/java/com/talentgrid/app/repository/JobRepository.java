package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}