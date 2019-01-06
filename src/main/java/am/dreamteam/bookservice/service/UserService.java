package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import java.util.List;

public interface UserService {
    User findUserById(int id);
    User login(String login, String pass);
    boolean regUser(User user);
    List<UsersAddBooks> getUsersAddBooks(User user);
}
