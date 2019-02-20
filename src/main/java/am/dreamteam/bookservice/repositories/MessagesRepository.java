package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByDialog(Dialog dialog);
    List<Message> getAllByDialogAndIsRead(Dialog dialog, Boolean isRead);

}
