package co.id.Itc25.Ticketing.dtos.employee;

import co.id.Itc25.Ticketing.models.Employee;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeDeleteResponseDto {

    private final String employeeId;
    private final String fullName;

    public static EmployeeDeleteResponseDto set(Employee employee){
        return new EmployeeDeleteResponseDto(employee.getEmployeeId(), employee.fetchFullName());
    }

    public static List<EmployeeDeleteResponseDto> toList(List<Employee> employees){
        List<EmployeeDeleteResponseDto> result = new ArrayList<>();
        for (Employee employee : employees){
            result.add(set(employee));
        }
        return result;
    }
}
