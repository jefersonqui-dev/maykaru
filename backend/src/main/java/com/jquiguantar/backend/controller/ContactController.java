package com.jquiguantar.backend.controller;

import com.jquiguantar.backend.dto.ContactFormDto;
import com.jquiguantar.backend.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // Endpoint público: Enviar formulario de contacto
    @PostMapping
    public ResponseEntity<String> submitContactForm(@Valid @RequestBody ContactFormDto contactFormDto) {
        contactService.processContactForm(contactFormDto);
        return new ResponseEntity<>("Contact form submitted successfully!", HttpStatus.OK);
    }
}