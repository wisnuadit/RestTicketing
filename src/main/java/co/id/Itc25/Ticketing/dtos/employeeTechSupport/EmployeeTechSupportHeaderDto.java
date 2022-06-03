package co.id.Itc25.Ticketing.dtos.employeeTechSupport;

import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.EmployeeTechSupportAvailable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeTechSupportHeaderDto {

    private final String employeeId;
    private final String fulllName;
    private final boolean available;

    public static EmployeeTechSupportHeaderDto set(EmployeeTechSupportAvailable employeeTechSupport){
        Employee employee = employeeTechSupport.getEmployee();
        return new EmployeeTechSupportHeaderDto(employeeTechSupport.getEmployeeID(), employee.fetchFullName(),
                employeeTechSupport.isAvailable());
    }

    public static List<EmployeeTechSupportHeaderDto> toList(List<EmployeeTechSupportAvailable> employeeTechSupportAvailables){
        List<EmployeeTechSupportHeaderDto> result = new ArrayList<>();
        for (EmployeeTechSupportAvailable employeeTechSupport : employeeTechSupportAvailables){
            result.add(set(employeeTechSupport));
        }
        return result;
    }
}
