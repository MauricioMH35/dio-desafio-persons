package org.dio.desafio.controllers;

import lombok.AllArgsConstructor;
import org.dio.desafio.dto.requests.PersonDTO;
import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.exceptions.PersonConflictException;
import org.dio.desafio.exceptions.PersonNotFoundException;
import org.dio.desafio.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/persons")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO save(@RequestBody @Valid PersonDTO personDTO) throws PersonConflictException {
        return service.save(personDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonDTO findByID(@PathVariable Long id) throws PersonNotFoundException {
        return service.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<PersonDTO> findAll() throws PersonNotFoundException {
        return service.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO)
            throws PersonNotFoundException {
        return service.updateById(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
        service.deleteById(id);
    }
}
