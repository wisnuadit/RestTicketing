package co.id.Itc25.Ticketing.dtos.employee;

import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.Job;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeHeaderDto {

    private final String employeeId;
    private final String fullName;
    private final LocalDate birthDate;
    private final String phone;
    private final String job;

    public static EmployeeHeaderDto set(Employee employee){
        Job job = employee.getJob();
        return new EmployeeHeaderDto(employee.getEmployeeId(), employee.fetchFullName(),
                employee.getBirthDate(), employee.getPhone(), job.getTitle());
    }

    public static List<EmployeeHeaderDto> toList(List<Employee> employees){
        List<EmployeeHeaderDto> result = new ArrayList<>();
        for (Employee employee : employees){
            result.add(set(employee));
        }
        return result;
    }

}
