package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.UserBooks;

import java.util.List;

public interface UsersBooksService {
    UserBooks getUsersBooksById(int id);
    boolean addUsersBooks(UserBooks usersBooks);
    List<UserBooks> getUsersBooksList();
}
