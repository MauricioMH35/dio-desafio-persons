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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository repository;

    private final PersonMapper mapper;

    public ResponseDTO save(PersonDTO personDTO) throws PersonConflictException {
        if (repository.findByCpf(personDTO.getCpf()) != null)
            throw new PersonConflictException();

        Person mapped = mapper.toEntity(personDTO);
        Person saved = repository.save(mapped);

        ResponseDTO response = createResponseMessage(
                "Person successfully created with \'id\' ",
                saved.getId());

        return response;
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person found = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return mapper.toDTO(found);
    }

    public List<PersonDTO> findAll() throws PersonNotFoundException {
        List<Person> persons = repository.findAll();
        if (persons.isEmpty())
            throw new PersonNotFoundException();

        return persons.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        Person found = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        personDTO.setId(id);
        Person updated = mapper.toEntity(personDTO);
        repository.save(updated);

        return createGenericResponseMessage(
                "Successfully updated data " + updated.getFirstname() + " on \'id\' (" + id + ')');
    }

    public void deleteById(Long id) throws PersonNotFoundException {
        repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        repository.deleteById(id);
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
