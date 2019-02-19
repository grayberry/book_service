package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogsRepository extends JpaRepository<Dialog, Long> {
    Dialog findByUserFromAndUserTo(User userFrom, User userTo);
    List<Dialog> findAllByUserFrom(User userFrom);
    List<Dialog> findAllByUserTo(User userTo);

}
