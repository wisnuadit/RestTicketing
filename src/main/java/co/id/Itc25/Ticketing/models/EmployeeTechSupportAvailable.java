package co.id.Itc25.Ticketing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "EmployeeTechSupportAvailable")
public class EmployeeTechSupportAvailable {

    @Id
    @Column(name = "EmployeeID")
    private String employeeID;

    @Column(name = "Available", nullable = false)
    private boolean available;

    @OneToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    public EmployeeTechSupportAvailable() {
        this.available = true;
    }
}
