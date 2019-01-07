package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.UsersAddBooksDAO;
import am.dreamteam.bookservice.dao.impl.UsersAddBooksDAOImpl;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.service.UsersAddBooksService;

import java.util.List;

public class UsersAddBooksServiceImpl implements UsersAddBooksService {

    UsersAddBooksDAO usersAddBooksDAO = new UsersAddBooksDAOImpl();

    @Override
    public UsersAddBooks getUsersAddBooksById(int id) {
        return usersAddBooksDAO.getUsersAddBooksById(id);
    }

    @Override
    public boolean addUsersAddBooks(UsersAddBooks usersAddBooks) {
        return usersAddBooksDAO.addUsersAddBooks(usersAddBooks);
    }

    @Override
    public List<UsersAddBooks> getUsersAddBooksList() {
        return usersAddBooksDAO.getUsersAddBooksList();
    }
}
