package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.AuthorDAO;
import am.dreamteam.bookservice.dao.impl.AuthorDAOImpl;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.service.AuthorService;

import java.util.Set;

public class AuthorServiceImpl implements AuthorService {

    AuthorDAO authorDAO = new AuthorDAOImpl();
    @Override
    public Author getAuthorById(int id) {
        return authorDAO.getAuthorById(id);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorDAO.getAuthorByName(name);
    }

    @Override
    public boolean addAuthor(Author author) {
        return authorDAO.addAuthor(author);
    }

    @Override
    public Set<Author> getAuthorsSet(String... fullName) {
        return authorDAO.getAuthorsSet(fullName);
    }

}
