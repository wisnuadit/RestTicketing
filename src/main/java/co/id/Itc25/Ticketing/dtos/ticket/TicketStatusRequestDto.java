package co.id.Itc25.Ticketing.dtos.ticket;

import lombok.Data;

@Data
public class TicketStatusRequestDto {

    private final String ticketId;
    private final String status;
}
