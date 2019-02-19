package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {
    void addAuthors(Set<Author> authors);
    Set<Author> getAuthorsSet(List list);
}
