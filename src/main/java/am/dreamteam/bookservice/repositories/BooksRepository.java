package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByTitleAndLanguage(String title, String language);
}
