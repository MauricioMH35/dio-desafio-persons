package org.dio.desafio.services;

import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.repositories.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.dio.desafio.utils.PersonUtil.createDTO;
import static org.dio.desafio.utils.PersonUtil.createEntity;
import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    public void setup() {
        person = createEntity();
        personDTO = createDTO();

        when(repository.findByCpf(personDTO.getCpf())).thenReturn(null);
        when((repository.save(any(Person.class)))).thenReturn(person);
        when(mapper.toEntity(any(PersonDTO.class))).thenReturn(person);
    }

    @Test
    @DisplayName("Expected Return Message When Person Save")
    public void save() throws PersonConflictException {
        // Should return null when searching for person using CPF
        String cpf = person.getCpf();
        Person existPerson = repository.findByCpf(cpf);
        assertNull(existPerson);

        // Must save and the entity must be the same as the saved entity
        Person saved = repository.save(person);
        assertEquals(createEntity(), saved);

        // Should show the message that it has been saved
        String response = service.save(createDTO()).getMessage();
        String expectedResponse = createResponseMessage(
                "Person successfully created with \'id\' ", 1L).getMessage();
        assertEquals(expectedResponse, response);
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }
}
