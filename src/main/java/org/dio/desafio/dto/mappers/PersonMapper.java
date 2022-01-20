package org.dio.desafio.dto.mappers;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toEntity(PersonDTO personDTO);

    PersonDTO toDTO(Person person);

}
