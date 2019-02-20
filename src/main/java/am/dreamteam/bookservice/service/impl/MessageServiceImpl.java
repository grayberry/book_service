package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.messages.Message;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.repositories.DialogsRepository;
import am.dreamteam.bookservice.repositories.MessagesRepository;
import am.dreamteam.bookservice.repositories.UsersRepository;
import am.dreamteam.bookservice.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessagesRepository messagesRepository;
    private DialogsRepository dialogsRepository;
    private UsersRepository usersRepository;

    public MessageServiceImpl(MessagesRepository messagesRepository,
                              UsersRepository usersRepository,
                              DialogsRepository dialogsRepository) {
        this.messagesRepository = messagesRepository;
        this.dialogsRepository = dialogsRepository;
        this.usersRepository = usersRepository;

    }

    @Override
    public void saveMessage(String userFrom, String userTo, String content) {

        User uFrom = usersRepository.findUserByUsername(userFrom);
        User uTo = usersRepository.findUserByUsername(userTo);
        Message message = new Message();
        Dialog dialog;
        if((dialog = dialogsRepository.findByUserFromAndUserTo(uFrom, uTo))==null){
            dialog = new Dialog();
            dialog.setUserFrom(uFrom);
            dialog.setUserTo(uTo);
            dialogsRepository.save(dialog);
        }
        message.setDialog(dialog);
        message.setContent(content);
        message.setRead(false);
        messagesRepository.save(message);
    }

    @Override
    public List<Message> findAllMessageByDialog(Dialog dialog) {

        return messagesRepository.findAllByDialog(dialog);
    }

    @Override
    public List<Message> findAllByDialogAndIsRead(Dialog dialog, Boolean isRead) {
        return messagesRepository.getAllByDialogAndIsRead(dialog, isRead);
    }


    @Override
    public void saveAll(List<Message> messages) {
        messagesRepository.saveAll(messages);
    }

}
