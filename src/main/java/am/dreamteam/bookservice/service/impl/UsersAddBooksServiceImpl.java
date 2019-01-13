package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.UsersBooksDAO;
import am.dreamteam.bookservice.dao.impl.UsersBooksDAOImpl;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UsersAddBooksService;

import java.util.List;

public class UsersAddBooksServiceImpl implements UsersAddBooksService {

    UsersBooksDAO usersBooksDAO = new UsersBooksDAOImpl();

    @Override
    public UserBooks getUsersAddBooksById(int id) {
        return usersBooksDAO.getUsersBooksById(id);
    }

    @Override
    public boolean addUsersAddBooks(UserBooks usersAddBooks) {
        return usersBooksDAO.addUsersBooks(usersAddBooks);
    }

    @Override
    public List<UserBooks> getUsersAddBooksList() {
        return usersBooksDAO.getUsersBooksList();
    }
}
