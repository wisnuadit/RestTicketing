package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketInsertResponseDto {

    private final String ticketId;
    private final LocalDate requestDate;
    private final LocalDate dueDate;
    private final String createdBy;
    private final String appointedTo;

    public static TicketInsertResponseDto set(Ticket ticket){
        return new TicketInsertResponseDto(ticket.getTicketId(), ticket.getRequestDate(), ticket.getDueDate(),
                ticket.getCreatedBy().getEmployeeId(), ticket.getAppointedTo().getEmployeeId());
    }
}
