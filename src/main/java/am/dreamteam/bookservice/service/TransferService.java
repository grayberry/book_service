package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;

public interface TransferService {
    Transfer getTransferById(int id);
    boolean createTransfer(User from, User to, UsersAddBooks fromBook, UsersAddBooks toBook);
    User getUserForTransfer(int id);
}
