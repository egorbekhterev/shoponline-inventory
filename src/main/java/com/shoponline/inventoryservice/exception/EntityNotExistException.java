package com.shoponline.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity does not exist.")
public class EntityNotExistException extends RuntimeException {

    public EntityNotExistException(String message) {
        super(message);
    }

    public EntityNotExistException(String format, Object... args) {
        super(String.format(format, args));
    }
}
