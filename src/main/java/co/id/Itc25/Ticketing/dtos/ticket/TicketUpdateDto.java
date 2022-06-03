package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

@Data
public class TicketUpdateDto {

    private final int id;
    private final int year;
//    private final String status = "CANCELLED";
}
