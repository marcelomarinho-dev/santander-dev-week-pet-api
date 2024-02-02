package dev.marcelomarinho.petapi.service.exception;

import java.io.Serial;

public class RecordNotFoundException extends BusinessException {

    @Serial
    private static final long serialVersionUID = -9147166517179242450L;

    public RecordNotFoundException() {
        super("Record not found.");
    }

}
