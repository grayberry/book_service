package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.repositories.DialogsRepository;
import am.dreamteam.bookservice.repositories.UsersRepository;
import am.dreamteam.bookservice.service.DialogService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DialogServiceImpl implements DialogService {

    private DialogsRepository dialogsRepository;
    private UsersRepository usersRepository;

    public DialogServiceImpl(DialogsRepository dialogsRepository, UsersRepository usersRepository) {
        this.dialogsRepository = dialogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Dialog findByUserFromAndUserTo(String usernameFrom, String usernameTo) {
        User userFrom = usersRepository.findUserByUsername(usernameFrom);
        User userTo = usersRepository.findUserByUsername(usernameTo);

        return dialogsRepository.findByUserFromAndUserTo(userFrom, userTo);
    }

    @Override
    public List<Dialog> findAllByUserFrom(String username) {
        User user = usersRepository.findUserByUsername(username);

        return dialogsRepository.findAllByUserFrom(user);
    }

    @Override
    public List<Dialog> findAllByUserTo(String username) {
        User user = usersRepository.findUserByUsername(username);

        return dialogsRepository.findAllByUserTo(user);
    }
    @Override
    public void save(Dialog dialog){
        dialogsRepository.save(dialog);
    }
}
