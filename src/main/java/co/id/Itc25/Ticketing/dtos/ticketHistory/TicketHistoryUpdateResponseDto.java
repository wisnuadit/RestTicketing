package co.id.Itc25.Ticketing.dtos.ticketHistory;

import co.id.Itc25.Ticketing.models.TicketHistory;
import lombok.Data;

@Data
public class TicketHistoryUpdateResponseDto {

    private final String ticketId;
    private final String status;

    public static TicketHistoryUpdateResponseDto set(TicketHistory ticketHistory){
        return new TicketHistoryUpdateResponseDto(ticketHistory.getTicketId(), ticketHistory.getStatus());
    }
}
