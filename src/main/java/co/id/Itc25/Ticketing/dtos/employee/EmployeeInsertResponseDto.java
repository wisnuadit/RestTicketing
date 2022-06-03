package co.id.Itc25.Ticketing.dtos.employee;

import co.id.Itc25.Ticketing.models.Employee;
import lombok.Data;

@Data
public class EmployeeInsertResponseDto {

    private final String employeeId;
    private final String fullName;

    public static EmployeeInsertResponseDto set(Employee employee){
        return new EmployeeInsertResponseDto(employee.getEmployeeId(), employee.fetchFullName());
    }
}
