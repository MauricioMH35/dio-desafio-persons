package org.dio.desafio.dto.mappers;

import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toEntity(PersonDTO personDTO);

    PersonDTO toDTO(Person person);

}
