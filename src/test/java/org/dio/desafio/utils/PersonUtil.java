package org.dio.desafio.utils;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.entities.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonUtil {

    private static final Long PERSON_ID = 1L;
    private static final String FIRST_NAME = "Fulano";
    private static final String LAST_NAME = "Beltrano";
    private static final String PERSON_CPF = "122.923.655-45";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 10, 1);

    public static PersonDTO createDTO() {
        return PersonDTO.builder()
                .id(PERSON_ID)
                .firstname(FIRST_NAME)
                .lastname(LAST_NAME)
                .cpf(PERSON_CPF)
                .birthDate(BIRTH_DATE.toString())
                .phones(Collections.singletonList(PhoneUtil.createDTO()))
                .isActive(true)
                .build();
    }

    public static Person createEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstname(FIRST_NAME)
                .lastname(LAST_NAME)
                .cpf(PERSON_CPF)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtil.createEntity()))
                .isActive(true)
                .build();
    }

}
