package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.UsersBooksDAO;
import am.dreamteam.bookservice.dao.impl.UsersBooksDAOImpl;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UsersBooksService;

import java.util.List;

public class UsersBooksServiceImpl implements UsersBooksService {

    UsersBooksDAO usersBooksDAO = new UsersBooksDAOImpl();

    @Override
    public UserBooks getUsersBooksById(int id) {
        return usersBooksDAO.getUsersBooksById(id);
    }

    @Override
    public boolean addUsersBooks(UserBooks usersBooks) {
        return usersBooksDAO.addUsersBooks(usersBooks);
    }

    @Override
    public List<UserBooks> getUsersBooksList() {
        return usersBooksDAO.getUsersBooksList();
    }
}
