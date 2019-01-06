package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.impl.UserDAOImpl;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public User findUserById(int id) {
        return userDAO.findUserById(id);
    }

    @Override
    public User login(String login, String pass) {
        if(userDAO.checkUser(login)){
            return userDAO.login(login, pass);
        }else return null;
    }

    @Override
    public boolean regUser(User user) {
        return userDAO.regUser(user);
    }

    @Override
    public List<UsersAddBooks> getUsersAddBooks(User user) {
        return userDAO.getUserBooks(user);
    }
}
