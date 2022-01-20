package org.dio.desafio.services;

import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.dio.desafio.utils.PersonUtil.createDTO;
import static org.dio.desafio.utils.PersonUtil.createEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonService service;

    private Person person;
    private PersonDTO personDTO;
    private List<PersonDTO> personsDTO;
    private ResponseDTO responseSavedDTO;
    private ResponseDTO responseUpdadedDTO;
    private ResponseDTO responseDeletedDTO;
    private ResponseDTO responsePermanentDeleteDTO;

    @BeforeEach
    public void setup() throws Exception {
        person = createEntity();
        personDTO = createDTO();
        personsDTO = Collections.singletonList(personDTO);

        responseSavedDTO = createResponseMessage("Person successfully created with \'id\' ", 1L);
        responseUpdadedDTO = createResponseMessage("Person successfully updated with \'id\' ", 1L);
        responseDeletedDTO = createResponseMessage("Disabled person with \'id\' ", 1L);
        responsePermanentDeleteDTO = createResponseMessage("Deleted person with \'id\' ", 1L);

        // Person Mapper process
        when(mapper.toEntity(personDTO)).thenReturn(person);

        // Save process
        when(repository.save(any(Person.class))).thenReturn(person);
        when(repository.findByCpf(person.getCpf())).thenReturn(null);
        when(service.save(any(PersonDTO.class))).thenReturn(responseSavedDTO);

        // Find By Id process
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        // Find All process
        when(repository.findAll()).thenReturn(Collections.singletonList(person));
    }

    @Test
    @DisplayName("Expected Return Message When Person Save")
    public void save() throws PersonConflictException {
        // Tests if the person does not exist in the database
        Person foundByCpf = repository.findByCpf(person.getCpf());
        assertEquals(foundByCpf, null);

        // Tests Entity to DTO conversion
        PersonDTO expectedDTO = createDTO();
        PersonDTO dto = mapper.toDTO(person);
        assertEquals(expectedDTO, dto);

        // Tests the return message
        ResponseDTO message = service.save(personDTO);
        assertEquals(responseSavedDTO, message);
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }

}
