package org.dio.desafio.services;

import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final PersonMapper mapper;

    @Autowired
    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseDTO save(PersonDTO personDTO) throws PersonConflictException {
        if(!repository.findByCpf(personDTO.getCpf()).equals(null))
            throw new PersonConflictException();

        Person saved = mapper.toEntity(personDTO);
        saved = repository.save(saved);

        ResponseDTO response = createResponseMessage(
                "Person successfully created with \'id\' ",
                saved.getId().toString());

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
