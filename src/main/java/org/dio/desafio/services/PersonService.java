package org.dio.desafio.services;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public String save(PersonDTO personDTO) {

    }
}
