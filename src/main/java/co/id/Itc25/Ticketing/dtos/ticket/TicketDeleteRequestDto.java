package co.id.Itc25.Ticketing.dtos.ticket;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TicketDeleteRequestDto {

    private final List<String> ticketIds;
    private final LocalDate requestDate;
}
