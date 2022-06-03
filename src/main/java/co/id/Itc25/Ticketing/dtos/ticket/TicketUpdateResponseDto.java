package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

@Data
public class TicketUpdateResponseDto {

    private final String ticketId;
    private final String status;

    public static TicketUpdateResponseDto set(Ticket ticket){
        return new TicketUpdateResponseDto(ticket.getTicketId(), ticket.getStatus().toString());
    }
}
