package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    boolean checkUser(String login);
    User login(String login, String pass);
    boolean regUser(User user);
    List<User> getAllUsersList();
    List<UsersAddBooks> getUsersAddBooks(User user);
}
