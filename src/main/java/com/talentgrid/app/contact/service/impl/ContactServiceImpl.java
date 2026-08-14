package com.talentgrid.app.contact.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.talentgrid.app.constants.ApplicationConstants;
import com.talentgrid.app.contact.service.IContactService;
import com.talentgrid.app.dto.ContactRequestDto;
import com.talentgrid.app.dto.ContactResponseDto;
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

        contact.setStatus(ApplicationConstants.NEW_MESSAGE);
        
        return contact;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgs() {
    
        List<Contact> contacts = contactRepository.findContactsByStatusOrderByCreatedAtAsc(ApplicationConstants.NEW_MESSAGE);

        List<ContactResponseDto> responseDtos = contacts.stream()
        .map(this::transformToDto)
        .collect(Collectors.toList());

        return responseDtos;

    }

    private ContactResponseDto transformToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto(
            contact.getId(),
            contact.getName(),
            contact.getEmail(),
            contact.getUserType(), 
            contact.getSubject(),
            contact.getMessage(), 
            contact.getStatus(), 
            contact.getCreatedAt());
        return contactResponseDto;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir) {
         // Create Sort object based on sortBy and sortDir parameters
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus(
                ApplicationConstants.NEW_MESSAGE, sort);
        List<ContactResponseDto> responseDtos = contacts.stream()
                .map(this::transformToDto)
                .collect(Collectors.toList());
        return responseDtos;
     }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(
            int pageNumber, int pageSize, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        Page<Contact> contactPage = contactRepository.findContactsByStatus(
                ApplicationConstants.NEW_MESSAGE, pageable);

                Page<ContactResponseDto> responseDtoPage = contactPage.map(this::transformToDto);
        return responseDtoPage;
    }

}
