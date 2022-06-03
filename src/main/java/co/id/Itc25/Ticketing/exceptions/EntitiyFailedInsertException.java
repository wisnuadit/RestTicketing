package co.id.Itc25.Ticketing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntitiyFailedInsertException extends ResponseStatusException {

    public EntitiyFailedInsertException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
