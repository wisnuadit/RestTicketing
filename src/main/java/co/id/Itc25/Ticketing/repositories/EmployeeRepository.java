package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
