package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
