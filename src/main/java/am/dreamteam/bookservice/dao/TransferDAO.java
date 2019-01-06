package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;

public interface TransferDAO {
    Transfer getTransferById(int id);
    boolean createTransfer(User from, User to, UsersAddBooks fromBook, UsersAddBooks toBook);
}
