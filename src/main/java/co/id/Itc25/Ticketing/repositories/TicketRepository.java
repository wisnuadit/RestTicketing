package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query(value = """
            SELECT * 
            FROM Ticket 
            WHERE YEAR(SUBSTRING(RIGHT(TicketID, 6), 1, 4)) = :year
            """, nativeQuery = true)
    List<Ticket> getTicketByYear(@Param("year") int year);

    @Query(value = """
            SELECT * 
            FROM Ticket 
            WHERE [Status] = :status
            """, nativeQuery = true)
    List<Ticket> getTicketByStatus(@Param("status") String status);

    @Query(value = """
            SELECT * 
            FROM Ticket 
            ORDER BY Urgency asc
            """, nativeQuery = true)
    List<Ticket> getTicketOrderByUrgency();

    @Query(value = """
            SELECT *\s
            FROM Ticket 
            WHERE YEAR(SUBSTRING(RIGHT(TicketID, 6), 1, 4)) = :year AND [Status] = :status
            ORDER BY Urgency asc
            """, nativeQuery = true)
    List<Ticket> getTicketByRequest(@Param("year") int year, @Param("status") String status);
}
