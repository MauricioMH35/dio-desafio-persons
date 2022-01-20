package org.dio.desafio.controllers;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/v1/persons")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@RequestBody @Valid PersonDTO personDTO) throws PersonConflictException {
        return service.save(personDTO);
    }

}
