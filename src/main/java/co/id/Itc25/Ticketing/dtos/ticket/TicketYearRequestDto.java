package co.id.Itc25.Ticketing.dtos.ticket;

import lombok.Data;

@Data
public class TicketYearRequestDto {

    private final String ticketId;
    private final int year;
}
