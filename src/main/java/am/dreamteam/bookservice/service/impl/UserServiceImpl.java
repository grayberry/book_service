package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.dao.impl.UserDAOImpl;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDAO userDAO = new UserDAOImpl();

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public boolean checkUser(String login){
        return userDAO.checkUser(login);
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
    public List<User> getAllUsersList() {
        return userDAO.getAllUsersList();
    }

    @Override
    public List<UserBooks> getUsersAddBooks(User user) {
        return userDAO.getUserBooks(user);
    }
}
