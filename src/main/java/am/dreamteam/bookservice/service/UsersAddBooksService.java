package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import java.util.List;

public interface UsersAddBooksService {
    UsersAddBooks getUsersAddBooksById(int id);
    boolean addUsersAddBooks(UsersAddBooks usersAddBooks);
    List<UsersAddBooks> getUsersAddBooksList();
}
