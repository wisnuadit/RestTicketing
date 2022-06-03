package co.id.Itc25.Ticketing.dtos.user;

import co.id.Itc25.Ticketing.dtos.employee.EmployeeHeaderDto;
import co.id.Itc25.Ticketing.models.Employee;
import co.id.Itc25.Ticketing.models.Job;
import co.id.Itc25.Ticketing.models.User;
import lombok.Data;

@Data
public class UserHeaderDto {

    private final String username;

    public static UserHeaderDto set(User user){
        return new UserHeaderDto(user.getUsername());
    }
}
