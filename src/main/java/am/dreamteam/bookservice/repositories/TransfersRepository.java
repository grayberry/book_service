package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransfersRepository extends JpaRepository<Transfer, Integer> {
    Transfer findByUserFromAndUserToAndUserBooksAndDone(User userFrom, User userTo, UserBooks userBooks, Boolean done);
    List<Transfer> findAllByUserToAndUserBooksAndDone(User userTo, UserBooks userBooks, Boolean done);
    List<Transfer> findAllByUserFromAndUserToAndDone(User userFrom, User userTo, Boolean done);
}
