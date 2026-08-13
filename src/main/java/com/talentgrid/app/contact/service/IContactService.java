package com.talentgrid.app.contact.service;

import java.util.List;

import com.talentgrid.app.dto.ContactRequestDto;
import com.talentgrid.app.dto.ContactResponseDto;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
    List<ContactResponseDto> fetchNewContactMsgs();
}
