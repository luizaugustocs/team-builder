package br.com.luizaugustocs.teambuilder.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String resourceName, UUID identifier) {
        super(String.format("Unable to find %s with id %s.", resourceName, identifier));
    }

    public NotFoundException(String message) {
        super(message);
    }
}
