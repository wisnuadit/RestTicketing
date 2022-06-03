package co.id.Itc25.Ticketing.dtos.ticketHistory;

import co.id.Itc25.Ticketing.models.TicketHistory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketHistoryInsertResponseDto {

    private final String ticketId;
    private final LocalDate completeDate;

    public static TicketHistoryInsertResponseDto set(TicketHistory ticketHistory){
        return new TicketHistoryInsertResponseDto(ticketHistory.getTicketId(), ticketHistory.getCompleteDate());
    }
}
