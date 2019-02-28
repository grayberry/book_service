package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.dto.RegisterRequest;
import am.dreamteam.bookservice.entities.users.User;

import java.util.List;

public interface UserService  {
    User findUserById(int id);
    int registerUser(RegisterRequest request);
    List<User> findAllUsers();
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    User findUserByVerificationCode(String code);
    int resend(String email);
    User disableUser(Integer id);
    User enableUser(Integer id);
}
