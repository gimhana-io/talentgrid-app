package com.talentgrid.app.contact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentgrid.app.dto.ContactRequestDto;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    public ResponseEntity<String> saveContadctMsg(@RequestBody ContactRequestDto contactRequestDto) {
       return null;
    }
}
