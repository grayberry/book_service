package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.domain.RegisterRequest;
import am.dreamteam.bookservice.entities.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  {
    User findUserById(int id);
    int registerUser(RegisterRequest request);
    List<User> findAllUsers();
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    User findUserByVerificationCode(String code);
    User disableUser(Integer id);
    User enableUser(Integer id);
}
