package co.id.Itc25.Ticketing.dtos.user;

import lombok.Data;

@Data
public class UserUsernamePasswordDto {

    private final String employeeId;
    private final String username;
    private final String password;
}
