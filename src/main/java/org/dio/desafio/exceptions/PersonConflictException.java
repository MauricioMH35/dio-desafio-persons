package org.dio.desafio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersonConflictException extends Exception {

    public PersonConflictException() {
        super("Registration of an existing person!");
    }

}
