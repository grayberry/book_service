package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.books.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Author findAuthorByFullName(String fullName);
}
