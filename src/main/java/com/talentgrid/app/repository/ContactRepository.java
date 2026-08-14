package com.talentgrid.app.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.talentgrid.app.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    List<Contact> findContactsByStatus(String status);

    //static sorting
    List<Contact> findContactsByStatusOrderByCreatedAtAsc(String status); 

    //dynamic sorting
    List<Contact> findContactsByStatus(String status, Sort sort);

}
