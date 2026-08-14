package com.talentgrid.app.contact.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.talentgrid.app.dto.ContactRequestDto;
import com.talentgrid.app.dto.ContactResponseDto;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
    
    List<ContactResponseDto> fetchNewContactMsgs();

    List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir);

    Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir);
}
