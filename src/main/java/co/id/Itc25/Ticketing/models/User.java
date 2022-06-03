package co.id.Itc25.Ticketing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "[User]")
public class User {

    @Id
    @Column(name = "UserID", nullable = false)
    private String userId;
    @Column(name = "Username", nullable = false)
    private String username;
    @Column(name = "[Password]", nullable = false)
    private String password;
    @Column(name = "[Enabled]", nullable = false)
    private boolean enabled;
    @Column(name = "[Role]", nullable = false)
    private String role;

    @OneToOne
    @JoinColumn(name = "UserID", nullable = false)
    private Employee employee;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }
}
