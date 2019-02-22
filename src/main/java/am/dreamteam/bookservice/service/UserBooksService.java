package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserBooksService {
    Page<UserBooks> findAll(Pageable pageable);
    UserBooks findUserBookById(int id);
    Page<UserBooks> findAllUserBooksByUserId(int id, Pageable pageable);
    Page<UserBooks> findAllUsersBooks(Pageable pageable);
    boolean addUserBooks(String username,List<Book> books);
    List<UserBooks> findAllUserBooksByUsername(String username);
    List<UserBooks> searchByAuthor(String term);
    List<UserBooks> searchByTitle(String term);
    List<UserBooks> getRand();

}
