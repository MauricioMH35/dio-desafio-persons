package org.dio.desafio.services;

import lombok.AllArgsConstructor;
import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.exceptions.PersonNotFoundException;
import org.dio.desafio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository repository;

    private final PersonMapper mapper;

    public ResponseDTO save(PersonDTO personDTO) throws PersonConflictException {
        if(repository.findByCpf(personDTO.getCpf()) != null)
            throw new PersonConflictException();

        Person mapped = mapper.toEntity(personDTO);
        Person saved = repository.save(mapped);

        ResponseDTO response = createResponseMessage(
                "Person successfully created with \'id\' ",
                saved.getId());

        return response;
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }

    private ResponseDTO createGenericResponseMessage(String message) {
        return ResponseDTO.builder()
                .message(message)
                .build();
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person found = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return mapper.toDTO(found);
    }

}
