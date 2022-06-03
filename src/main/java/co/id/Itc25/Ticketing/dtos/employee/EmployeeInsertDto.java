package co.id.Itc25.Ticketing.dtos.employee;

import co.id.Itc25.Ticketing.models.Employee;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class EmployeeInsertDto {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;

    public Employee convert(){
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return new Employee(employeeId, firstName, lastName, LocalDate.parse(birthDate, formatTanggal),
                phone);
    }

}
