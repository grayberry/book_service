package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    String checkUser(String login);
    User login(String login, String pass);
    User regUser(String username, String pass, String email, String phoneNumber, String sex);
    List<User> getAllUsersList();
    List<UserBooks> getUsersBooks(User user);
}
