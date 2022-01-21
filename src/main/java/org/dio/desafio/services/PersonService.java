package org.dio.desafio.services;

import lombok.AllArgsConstructor;
import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.mappers.PhoneMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.requests.PhoneDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.entities.Phone;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.exceptions.PersonNotFoundException;
import org.dio.desafio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository repository;

    private final PersonMapper mapper;

    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;
    public List<PhoneDTO> findAll() {
        return phoneRepository.findAll().stream()
                .map(phoneMapper::toDTO)
                .collect(Collectors.toList());
    }

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

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        PersonDTO found = mapper
                .toDTO(repository.findById(id)
                        .orElseThrow(() -> new PersonNotFoundException(id)));
        return found;
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
}
