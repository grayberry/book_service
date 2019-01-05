package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import java.io.Serializable;
import java.util.List;

public interface UsersAddBooksDAO extends Serializable {
    UsersAddBooks findUsersAddBooksById(int id);
    boolean addUsersAddBooks(UsersAddBooks usersAddBooks);
    List<UsersAddBooks> getListUsersAddBooks();
}
