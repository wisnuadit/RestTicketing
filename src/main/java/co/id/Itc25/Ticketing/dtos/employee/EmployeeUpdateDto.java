package co.id.Itc25.Ticketing.dtos.employee;

import co.id.Itc25.Ticketing.models.Employee;
import lombok.Data;

@Data
public class EmployeeUpdateDto {

    private final String employeeId;
    private final String firstName;
    private final String lastName;

    public static EmployeeUpdateDto set(Employee employee){
        return new EmployeeUpdateDto(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName());
    }
}
