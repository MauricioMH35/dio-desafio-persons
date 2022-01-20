package org.dio.desafio.services;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.exceptions.PersonConflictException;
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

    public ResponseDTO save(PersonDTO personDTO) throws PersonConflictException {
        if(!repository.findByCpf(personDTO.getCpf()).equals(null))
            throw new PersonConflictException();

        repository.save(personDTO);

        ResponseDTO response = createGenericResponseMessage("Person successfully created with \'id\' ", );
        return null;
    }

    private ResponseDTO createResponseMessage(String message, String data) {
        return ResponseDTO.builder()
                .message(message + data)
                .build();
    }

    private ResponseDTO createGenericResponseMessage(String message) {
        return ResponseDTO.builder()
                .message(message)
                .build();
    }
}
