package co.id.Itc25.Ticketing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityBadRequestException extends ResponseStatusException {

    public EntityBadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
