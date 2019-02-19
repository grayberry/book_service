package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.messages.Dialog;

import java.util.List;

public interface DialogService {
    Dialog findByUserFromAndUserTo(String usernameFrom, String usernameTo);
    List<Dialog> findAllByUserFrom(String username);
    List<Dialog> findAllByUserTo(String username);
    void save(Dialog dialog);
}
