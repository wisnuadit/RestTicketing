package co.id.Itc25.Ticketing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Job")
public class Job {

    @Id
    @Column(name = "JobID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;
    @Column(name = "Title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "job", cascade = CascadeType.PERSIST)
    private List<Employee> employees;
}
