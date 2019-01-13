package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.users.UserBooks;

import java.io.Serializable;
import java.util.List;

public interface UsersBooksDAO extends Serializable {
    UserBooks getUsersBooksById(int id);
    boolean addUsersBooks(UserBooks usersAddBooks);
    List<UserBooks> getUsersBooksList();
}
