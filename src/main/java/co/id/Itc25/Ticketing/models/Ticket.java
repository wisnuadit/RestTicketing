package co.id.Itc25.Ticketing.models;

import co.id.Itc25.Ticketing.enums.StatusTicket;
import co.id.Itc25.Ticketing.enums.Urgency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @Column(name = "TicketId", nullable = false)
    private String ticketId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Details", nullable = false)
    private String details;

    @Column(name = "RequestDate", nullable = false)
    private LocalDate requestDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Urgency", nullable = false)
    private Urgency urgency;

    @Enumerated(EnumType.STRING)
    @Column(name = "[Status]", nullable = false)
    private StatusTicket status;

    @ManyToOne
    @JoinColumn(name = "CreatedBy", nullable = false)
    private Employee createdBy;

    @ManyToOne
    @JoinColumn(name = "AppointedTo", nullable = false)
    private Employee appointedTo;

    public Ticket(String title, String details, LocalDate requestDate, LocalDate dueDate, Urgency urgency) {
        this.title = title;
        this.details = details;
        this.requestDate = requestDate;
        this.dueDate = dueDate;
        this.urgency = urgency;
        this.status = StatusTicket.IN_PROGRESS;
    }
}
