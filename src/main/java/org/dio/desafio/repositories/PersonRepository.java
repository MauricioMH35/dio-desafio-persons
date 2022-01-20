package org.dio.desafio.repositories;

import org.dio.desafio.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByCpf(String cpf);

}
