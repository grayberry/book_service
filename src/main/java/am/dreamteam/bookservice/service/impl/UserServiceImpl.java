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
    public String checkUser(String login){
        return userDAO.checkUser(login);
    }

    @Override
    public User login(String login, String pass) {
        String loginType;
        if((loginType = userDAO.checkUser(login)) != null){
            return userDAO.login(login, pass, loginType);
        }else return null;
    }

    @Override
    public User regUser(String username, String pass, String email, String phoneNumber, String sex) {
        return userDAO.regUser(username, pass, email, phoneNumber, sex);
    }

    @Override
    public List<User> getAllUsersList() {
        return userDAO.getAllUsersList();
    }

    @Override
<<<<<<< HEAD
    public List<UserBooks> getUsersAddBooks(User user) {
=======
    public List<UserBooks> getUsersBooks(User user) {
>>>>>>> 858a129cb3629c80769b762af93ed5133d54ffcf
        return userDAO.getUserBooks(user);
    }
}
