package org.dio.desafio.services;

import org.dio.desafio.dto.mappers.PersonMapper;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.entities.Person;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.exceptions.PersonNotFoundException;
import org.dio.desafio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        when(repository.findAll()).thenReturn(Collections.singletonList(person));
        when(repository.save(any(Person.class))).thenReturn(person);

        when(mapper.toEntity(any(PersonDTO.class))).thenReturn(person);
        when(mapper.toDTO(any(Person.class))).thenReturn(personDTO);
    }

    @Test
    @DisplayName("When Save Person Then Return ResponseDTO Succeeded")
    public void save_Test() throws PersonConflictException {
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
    @DisplayName("When Find By Id Person Then Return PersonDTO Succeeded")
    public void findById_Test() throws PersonNotFoundException {
        // Should return the Person found using the Id
        Person found = repository.findById(1L)
                .orElseThrow(() -> new PersonNotFoundException(1L));
        assertEquals(person, found);

        // Should return entity to DTO conversion
        PersonDTO converted = mapper.toDTO(found);
        assertEquals(personDTO, converted);

        // Should return the person found by Id
        PersonDTO actualPerson = service.findById(1L);
        PersonDTO expectedPerson = personDTO;
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    @DisplayName("When Find All Persons Then Return List PersonDTOs Succeeded")
    public void findAll_Test() throws PersonNotFoundException {
        // Should return the list of people
        List<Person> persons = repository.findAll();
        assertEquals(persons, Collections.singletonList(person));

        // Should return entity to DTO conversion list
        List<PersonDTO> personsDTO = persons.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        assertEquals(personsDTO, Collections.singletonList(personDTO));

        // Should return the list PersonDTO
        List<PersonDTO> actualPersons = service.findAll();
        List<PersonDTO> expectedPersons = personsDTO;
        assertEquals(expectedPersons, actualPersons);
    }

    @Test
    @DisplayName("When Save Is Thrown Person Conflict Exception Then Assertion Succeeded")
    public void personConflictException_Test() {
        // Should return the exception when trying to save a person with CPF already registred
        Exception exception = assertThrows(PersonConflictException.class, () -> {
            when(repository.findByCpf(personDTO.getCpf())).thenReturn(person);
            service.save(personDTO);
        });

        // Should check the exception message
        String expectedMessage = "Registration of an existing person!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("When Find By Id Is Thrown Person Not Found Exception Then Assertion Succeeded")
    public void personNotFoundException_Test() {
        // Should return an exception when trying to find a person with non-existing Id
        Long id = 2L;
        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
            service.findById(id);
        });

        // Should check the exception message
        String expectedMessage = "Could not found the person with the \'id\' (" + id + ')';
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("When Find All Is Thrown Person Not Found Exception Then Assertion Succeeded")
    public void personNotFoundException_TestFindAll() {
        // Should return an exception when not found persons
        Exception exception = assertThrows(PersonNotFoundException.class, () -> {
            when(repository.findAll()).thenReturn(Collections.emptyList());
            service.findAll();
        });

        // Should check the exception message
        String expectedMessage = "Could not find the requested data";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }
}
