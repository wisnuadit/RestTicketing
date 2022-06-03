package co.id.Itc25.Ticketing.dtos.ticketHistory;

import co.id.Itc25.Ticketing.models.TicketHistory;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class TicketHistoryInsertDto {

    private String description;
    private String completeDate;
    private int year;
    private int id;

    public TicketHistory convert(){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return new TicketHistory(description, LocalDate.parse(completeDate, formatTanggal));
    }
}
