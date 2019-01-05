package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import java.io.Serializable;
import java.util.Set;

public interface BookDAO extends Serializable {
    Book getBookById(int id);
    Book checkBookUnique(String title, Set<Author> authors, String language);
    boolean addBook(Book book);

}
