package com.talentgrid.app.contact.service.impl;

import java.time.Instant;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.talentgrid.app.contact.service.IContactService;
import com.talentgrid.app.dto.ContactRequestDto;
import com.talentgrid.app.entity.Contact;
import com.talentgrid.app.repository.ContactRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        boolean result = false;
        Contact contact = contactRepository.save(transformToEntity(contactRequestDto));
        if(contact != null && contact.getId() != null) {
            result = true;
        }
        return result;
    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto, contact);

        contact.setCreatedAt(Instant.now());
        contact.setCreatedBy("System");
        contact.setStatus("NEW");
        
        return contact;
    }

}
