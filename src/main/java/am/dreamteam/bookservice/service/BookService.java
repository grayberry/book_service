package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;

import java.util.Set;

public interface BookService {
    Book getBookById(int id);
    Book checkBookUnique(String title, String language, Set<Author> authors);
    boolean addBook(Book book, Set<Author> authors, Set<Category> categories);
}
