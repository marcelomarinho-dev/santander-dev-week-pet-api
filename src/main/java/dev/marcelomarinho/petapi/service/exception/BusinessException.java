package dev.marcelomarinho.petapi.service.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3888358985851879886L;

    public BusinessException(String message) {
        super(message);
    }

}
