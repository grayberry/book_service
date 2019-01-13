package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;

public interface TransferService {
    Transfer getTransferById(int id);
    boolean createTransfer(User from, User to, UserBooks fromBook, UserBooks toBook);
    User getUserForTransfer(int id);
}
