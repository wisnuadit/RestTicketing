package co.id.Itc25.Ticketing.dtos.ticketHistory;

import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.TicketHistory;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketHistoryHeaderDto {

    private final String ticketId;
    private final String description;
    private final LocalDate completeDate;
    private final String status;
    private final String approvedBy;

    public static TicketHistoryHeaderDto set(TicketHistory ticketHistory){
        Employee approvedBy = ticketHistory.getApprovedBy();
        return new TicketHistoryHeaderDto(ticketHistory.getTicketId(), ticketHistory.getDescription(),
                ticketHistory.getCompleteDate(), ticketHistory.getStatus(), approvedBy.getEmployeeId());
    }

    public static List<TicketHistoryHeaderDto> toList(List<TicketHistory> ticketHistories){
        List<TicketHistoryHeaderDto> result = new ArrayList<>();
        for (TicketHistory ticketHistory : ticketHistories){
            result.add(set(ticketHistory));
        }
        return result;
    }
}
