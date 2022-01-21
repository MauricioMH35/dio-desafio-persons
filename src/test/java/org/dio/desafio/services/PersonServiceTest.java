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

import static org.dio.desafio.utils.PersonUtil.createDTO;
import static org.dio.desafio.utils.PersonUtil.createEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    private ResponseDTO responseSaveDTO;

    @BeforeEach
    public void setup() throws Exception {
        person = createEntity();
        personDTO = createDTO();

        responseSaveDTO = createResponseMessage("Person successfully created with \'id\' ", 1L);

        when(repository.findByCpf(any(String.class))).thenReturn(null);
        when(repository.save(any(Person.class))).thenReturn(person);

        when(mapper.toDTO(any(Person.class))).thenReturn(personDTO);
    }

    @Test
    @DisplayName("Expected Return Message When Person Save")
    public void save() throws PersonConflictException {
        // Should return null when searching for person using CPF
        Person exitPerson = repository.findByCpf(personDTO.getCpf());
        assertNull(exitPerson);

        // Must convert an entity to DTO
        PersonDTO expectedMapper = mapper.toDTO(person);
        assertEquals(expectedMapper, personDTO);

        // Must save and the entity must be the same as the saved entity
        Person expectedSaved = repository.save(person);
        assertEquals(expectedSaved, person);

        // Should show the message that it has been saved
        String expectedResponse = responseSaveDTO.getMessage();
        String message = "Person successfully created with \'id\' 1";
        assertEquals(expectedResponse, message);
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }

}
