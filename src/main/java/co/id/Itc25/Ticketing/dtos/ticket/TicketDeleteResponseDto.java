package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.dtos.employee.EmployeeDeleteResponseDto;
import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketDeleteResponseDto {

    private final String ticketId;
    private final LocalDate requestDate;

    public static TicketDeleteResponseDto set(Ticket ticket){
        return new TicketDeleteResponseDto(ticket.getTicketId(), ticket.getRequestDate());
    }

    public static List<TicketDeleteResponseDto> toList(List<Ticket> tickets){
        List<TicketDeleteResponseDto> result = new ArrayList<>();
        for (Ticket ticket : tickets){
            result.add(set(ticket));
        }
        return result;
    }
}
