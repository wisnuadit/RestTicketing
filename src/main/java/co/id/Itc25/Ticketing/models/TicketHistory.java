package co.id.Itc25.Ticketing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TicketHistory")
public class TicketHistory {

    @Id
    @Column(name = "TicketID", nullable = false)
    private String ticketId;

    @Column(name = "[Description]", nullable = false)
    private String description;
    @Column(name = "CompleteDate", nullable = false)
    private LocalDate completeDate;
    @Column(name = "[Status]", nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "TicketID", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "ApprovedBy")
    private Employee approvedBy;

    public TicketHistory(String description, LocalDate completeDate) {
        this.description = description;
        this.completeDate = completeDate;
    }
}
