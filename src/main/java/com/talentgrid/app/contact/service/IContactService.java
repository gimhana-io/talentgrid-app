package com.talentgrid.app.contact.service;

import com.talentgrid.app.dto.ContactRequestDto;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
}
