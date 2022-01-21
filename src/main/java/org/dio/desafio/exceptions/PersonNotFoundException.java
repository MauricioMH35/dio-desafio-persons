package org.dio.desafio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Long id) {
        super("Could not found the person with the \'id\' (" + id + ")");
    }

    public PersonNotFoundException(String data) {
        super("Could not found the person with the \'data\' (" + data + ")");
    }

}
