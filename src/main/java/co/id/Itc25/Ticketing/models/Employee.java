package co.id.Itc25.Ticketing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @Column(name = "EmployeeID", nullable = false)
    private String employeeId;
    @Column(name = "FirstName", nullable = false)
    private String firstName;
    @Column(name = "LastName", nullable = false)
    private String lastName;
    @Column(name = "BirthDate")
    private LocalDate birthDate;
    @Column(name = "Phone", nullable = false)
    private String phone;
    @Column(name = "IsActive", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "JobID")
    private Job job;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    private List<Ticket> ticketsCreatedBy;

    @OneToMany(mappedBy = "appointedTo", cascade = CascadeType.PERSIST)
    private List<Ticket> ticketsAppointedTo;

    public Employee(String employeeId, String firstName, String lastName, LocalDate birthDate, String phone) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public String fetchFullName(){
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee() {
    }
}
