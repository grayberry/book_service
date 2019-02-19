package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Book;

public interface BookService {
    Book checkBookUnique(Book book);
}
