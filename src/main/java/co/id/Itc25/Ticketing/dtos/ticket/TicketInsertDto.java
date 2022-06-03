package co.id.Itc25.Ticketing.dtos.ticket;

import co.id.Itc25.Ticketing.enums.Urgency;
import co.id.Itc25.Ticketing.models.Ticket;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class TicketInsertDto {

    private String title;
    private String details;
    private String requestDate;
    private String dueDate;
    private String urgency;
    private String createdBy;
    private String appointedTo;

    public Ticket convert(){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return new Ticket(title, details, LocalDate.parse(requestDate, formatTanggal),
                LocalDate.parse(dueDate, formatTanggal), Urgency.valueOf(urgency));
    }
}
