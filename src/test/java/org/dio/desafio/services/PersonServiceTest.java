package org.dio.desafio.services;

import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.exceptions.PersonNotFoundException;
import org.dio.desafio.repositories.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.dio.desafio.utils.PersonUtil.createDTO;
import static org.dio.desafio.utils.PersonUtil.createEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.findByCpf(personDTO.getCpf())).thenReturn(null);
        when(repository.save(any(Person.class))).thenReturn(person);

        when(mapper.toEntity(any(PersonDTO.class))).thenReturn(person);
        when(mapper.toDTO(any(Person.class))).thenReturn(personDTO);
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

    @Test
    public void findById() throws PersonNotFoundException {
        Person found = repository.findById(1L)
                .orElseThrow(() -> new PersonNotFoundException(1L));
        assertEquals(person, found);
    }

    @Test
    @DisplayName("When Save Is Thrown Person Conflict Exception Then Assertion Succeeded")
    public void whenSaveIsThrownPersonConflictExceptionThenAssertionSucceeded() {
        Exception exception = assertThrows(PersonConflictException.class, () -> {
            when(repository.findByCpf(personDTO.getCpf())).thenReturn(person);
            service.save(personDTO);
        });

        String expectedMessage = "Registration of an existing person!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("When Find By Id Is Thrown Person Not Found Exception Then Assertion Succeeded")
    public void whenFindByIdIsThrownPersonNotFoundExceptionThenAssertionSucceeded() {
        Long id = 2L;
        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
            service.findById(id);
        });

        String expectedMessage = "Could not found the person with the \'id\' (" + id + ')';
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }
}
