package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.impl.TransferDAOImpl;
import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.service.TransferService;
import am.dreamteam.bookservice.service.UsersAddBooksService;

public class TransferServiceImpl implements TransferService {
    TransferDAOImpl transferDAO = new TransferDAOImpl();
    @Override
    public Transfer getTransferById(int id) {
        return transferDAO.getTransferById(id);
    }

    @Override
    public boolean createTransfer(User from, User to, UsersAddBooks fromBook, UsersAddBooks toBook) {
        return transferDAO.createTransfer(from, to, fromBook, toBook);
    }
}
