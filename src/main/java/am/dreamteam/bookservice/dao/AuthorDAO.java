package am.dreamteam.bookservice.dao;

import am.dreamteam.bookservice.entities.books.Author;

import java.io.Serializable;

public interface AuthorDAO extends Serializable {
    Author getAuthorById(int id);
    Author getAuthorByName(String name);
    boolean addAuthor(Author author);
}