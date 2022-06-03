package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.EmployeeTechSupportAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeTechSupportAvailableRepository extends JpaRepository<EmployeeTechSupportAvailable, String> {

    @Query(value = """
            SELECT * 
            FROM EmployeeTechSupportAvailable 
            WHERE Available = :available
            """, nativeQuery = true)
    List<EmployeeTechSupportAvailable> getEmployeeIsAvailable(@Param("available") int available);
}
