package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public ResponseEntity<User> fetchAllUsers() {
//        return new ResponseEntity<>(userService.getUserByUsername(getPrincipal()), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/user", method = RequestMethod.PUT)
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        User current = userService.getUserByUsername(getPrincipal());
//
//        current = setFields(current, user);
//        userService.update(current);
//        return new ResponseEntity<>(current, HttpStatus.OK);
//    }
//
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsers(@RequestBody User user) {
        System.out.println(user.getUsername());
        User current = userService.findUserById(user.getId());
        current = setFields(current, user);
        userService.save(current);
        return new ResponseEntity<>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/enable", method = RequestMethod.PUT)
    public ResponseEntity<User> enableUser(@RequestBody User user) {
        user = userService.enableUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users")
    public ResponseEntity<List<User>> getUsersList() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) {
        userService.disableUser(id);
    }

    public User setFields(User current, User edited) {
        System.out.println(edited.getUsername());
        if(edited.getUsername() != null) {
            current.setUsername(edited.getUsername());
        }
        if(edited.getAltername() != null) {
            current.setAltername(edited.getAltername());
        }
        if (edited.getSex() != null) {
            current.setSex(edited.getSex());
        }

        return current;
    }
}

