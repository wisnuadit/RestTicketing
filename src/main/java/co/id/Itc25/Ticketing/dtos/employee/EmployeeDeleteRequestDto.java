package co.id.Itc25.Ticketing.dtos.employee;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDeleteRequestDto {

    private final List<String> employeeIds;
    private final String fullName;
}
