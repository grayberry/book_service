package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.TransferDAO;
import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.dao.impl.TransferDAOImpl;
import am.dreamteam.bookservice.dao.impl.UserDAOImpl;
import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.service.TransferService;

public class TransferServiceImpl implements  TransferService {
    TransferDAO transferDAO = new TransferDAOImpl();
    UserDAO userDAO = new UserDAOImpl();

    @Override
    public Transfer getTransferById(int id) {
        return transferDAO.getTransferById(id);
    }

    @Override
    public boolean createTransfer(User from, User to, UsersAddBooks fromBook, UsersAddBooks toBook) {
        return transferDAO.createTransfer(from, to, fromBook, toBook);
    }

    @Override
    public User getUserForTransfer(int id) {
        try{
            return userDAO.getUserById(id);
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

}
