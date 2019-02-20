package am.dreamteam.bookservice.service;


import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.messages.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(String userFrom, String userTo, String content);
    List<Message> findAllMessageByDialog(Dialog dialog);
    List<Message> findAllByDialogAndIsRead(Dialog dialog, Boolean isRead);
    void saveAll(List<Message> messages);

}
