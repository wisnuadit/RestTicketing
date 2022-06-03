package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.enums.StatusTicket;
import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketHeaderDto {

    private final String ticketId;
    private final String title;
    private final String details;
    private final LocalDate requestDate;
    private final LocalDate dueDate;
    private final String urgency;
    private final String createdBy;
    private final String appointedTo;
    private final String status;

    public static TicketHeaderDto set(Ticket ticket){
        Employee createdBy = ticket.getCreatedBy();
        Employee appointedTo = ticket.getAppointedTo();

        return new TicketHeaderDto(ticket.getTicketId(), ticket.getTitle(), ticket.getDetails(), ticket.getRequestDate(),
                ticket.getDueDate(), ticket.getUrgency().toString(), createdBy.getEmployeeId(), appointedTo.getEmployeeId(),
                ticket.getStatus().toString());
    }

    public static List<TicketHeaderDto> toList(List<Ticket> tickets){
        List<TicketHeaderDto> result = new ArrayList<>();
        for (Ticket ticket : tickets){
            result.add(set(ticket));
        }
        return result;
    }
}
