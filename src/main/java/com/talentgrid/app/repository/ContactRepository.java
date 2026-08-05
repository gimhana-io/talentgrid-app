package com.talentgrid.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    
}
