package io.project.fastwork.controller.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestTypeWorkAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RestTypeWorkAlreadyExistsException() {
        super();
    }

    public RestTypeWorkAlreadyExistsException(String message) {
        super(message);
    }
}
