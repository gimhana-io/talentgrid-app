package com.talentgrid.app.contact.service.impl;

import org.springframework.stereotype.Service;

import com.talentgrid.app.contact.service.IContactService;
import com.talentgrid.app.dto.ContactRequestDto;

@Service
public class ContactServiceImpl implements IContactService {

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        return false;
    }

}
