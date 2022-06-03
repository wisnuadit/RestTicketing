package co.id.Itc25.Ticketing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
