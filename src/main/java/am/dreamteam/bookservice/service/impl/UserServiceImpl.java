package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.domain.RegisterRequest;
import am.dreamteam.bookservice.domain.Role;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.repositories.UsersRepository;
import am.dreamteam.bookservice.service.UserService;
import am.dreamteam.bookservice.util.MailSendHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;
    private MailSendHelper mailSendHelper;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
                           MailSendHelper mailSendHelper) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.mailSendHelper = mailSendHelper;
    }

    @Override
    public User findUserById(int id) {
        return usersRepository.findById(id).get();
    }

    @Override
    public int registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        user.setEmail(request.getEmail().toLowerCase());

        if(findByUsername(user.getUsername())!=null){
            return -1;
        }
        if(findByEmail(user.getEmail())!=null){
            return 0;
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setSex(request.getSex());
        user.setPhoto("avatars/" + request.getSex() + ".png");
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        save(user);
        mailSendHelper.sendVerificationToken(user);
        return 1;
    }

    public User disableUser(Integer id) {
        User user = findUserById(id);
        user.setActive(false);
        save(user);

        return user;
    }

    @Override
    public User enableUser(Integer id) {
        User user = findUserById(id);
        user.setActive(true);
        save(user);

        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    @Override
    public User save(User user){
        return usersRepository.save(user);
    }

    @Override
    public User findUserByVerificationCode(String code) {
        return usersRepository.findUserByActivationCode(code);
    }

    @Override
    public int resend(String email) {
        User user = usersRepository.findUserByEmail(email);
        if (user == null) {
            return -1;
        }
        if (user.getActivationCode() == null) {
            return 0;
        }else {
            mailSendHelper.sendVerificationToken(user);
            return 1;
        }


    }

/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(findByEmail(username)!=null){
            username = findByEmail(username).getUsername();
        }
        User user = findByUsername(username);
        try {
            org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")
                    ));


            return u;
        } catch (UsernameNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }*/
}
