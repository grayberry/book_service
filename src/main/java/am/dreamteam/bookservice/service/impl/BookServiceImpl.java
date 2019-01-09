package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.BookDAO;
import am.dreamteam.bookservice.dao.impl.BookDAOImpl;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.service.BookService;

import java.util.Set;

public class BookServiceImpl implements BookService {

    BookDAO bookDAO = new BookDAOImpl();

    @Override
    public Book getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    @Override
    public Book checkBookUnique(String title, String language, Set<Author> authors) {
        return bookDAO.checkBookUnique(title, language, authors);
    }

    @Override
    public boolean addBook(Book book, Set<Author> authors, Set<Category> categories) {

        return bookDAO.addBook(book, authors, categories);
    }
}
