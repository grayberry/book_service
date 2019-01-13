package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.UserBooks;

import java.util.List;

public interface UsersAddBooksService {
    UserBooks getUsersAddBooksById(int id);
    boolean addUsersAddBooks(UserBooks usersAddBooks);
    List<UserBooks> getUsersAddBooksList();
}
