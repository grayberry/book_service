package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Author;

import java.util.Set;

public interface AuthorService {
    Author getAuthorById(int id);
    Author getAuthorByName(String name);
    boolean addAuthor(Author author);
    Set<Author> getAuthorsList(String...fullName);
}
