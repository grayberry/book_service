package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import java.io.Serializable;
import java.util.List;

public interface UserDAO extends Serializable {
    User findUserById(int id);
    boolean checkUser(String login);
    boolean regUser(User user);
    User login(String login, String password);
    List<UsersAddBooks> getUserBooks(User user);

}
