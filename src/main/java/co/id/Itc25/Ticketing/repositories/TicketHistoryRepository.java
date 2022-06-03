package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, String> {
}
