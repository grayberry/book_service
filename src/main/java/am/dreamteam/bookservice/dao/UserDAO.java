package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;

import java.io.Serializable;
import java.util.List;

public interface UserDAO extends Serializable {
    User getUserById(int id);
    String checkUser(String login);
    User regUser(String username, String pass, String email, String phoneNumber, String sex);
    User login(String username, String password, String loginType);
    List<User> getAllUsersList();
    List<UserBooks> getUserBooks(User user);

}
