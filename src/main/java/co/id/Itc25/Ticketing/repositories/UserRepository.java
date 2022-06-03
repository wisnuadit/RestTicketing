package co.id.Itc25.Ticketing.repositories;

import co.id.Itc25.Ticketing.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    //orElseThrow()...
    Optional<User> findByUsername(String username);
}
