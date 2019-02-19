package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByActivationCode(String activationCode);
}
