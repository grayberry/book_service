package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.repositories.AuthorsRepository;
import am.dreamteam.bookservice.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorsRepository authorsRepository;

    public AuthorServiceImpl(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Override
    public void addAuthors(Set<Author> authors) {
        for(Author author: authors){
            authorsRepository.save(author);
        }
    }

    @Override
    public Set<Author> getAuthorsSet(List list) {
        Set<Author> authors = new HashSet<>();
        Author author;
        for (Object a :list){
            if((author = authorsRepository.findAuthorByFullName(a.toString()))==null){
                author = new Author(a.toString());
                authorsRepository.save(author);
            }
            authors.add(author);
        }
        return authors;

    }
}
