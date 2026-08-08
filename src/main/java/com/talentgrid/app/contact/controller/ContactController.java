package com.talentgrid.app.contact.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentgrid.app.contact.service.IContactService;
import com.talentgrid.app.dto.ContactRequestDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService contactService;

    @PostMapping(version="1.0")
    public ResponseEntity<String> saveContadctMsg(@RequestBody @Valid ContactRequestDto contactRequestDto) {
        boolean isSaved = contactService.saveContact(contactRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Contact message saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save contact message.");
        }
    }

    @GetMapping(version="1.0")
    public ResponseEntity<String> fetchOpenContacts(@RequestParam 
        @Validated 
        @Size(min = 4, message = "Status length should be of minium 4 characters")
        @NotBlank(message = "Status cannot be blank") 
     String status){
        return ResponseEntity.ok("These are the contacts with the give status: " + status);
    }
}
