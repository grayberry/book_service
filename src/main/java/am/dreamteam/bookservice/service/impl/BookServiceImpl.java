package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.repositories.BooksRepository;
import am.dreamteam.bookservice.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    private BooksRepository booksRepository;

    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book checkBookUnique(Book book) {

        List<Book> books = booksRepository.findAllByTitleAndLanguage(book.getTitle(), book.getLanguage());
        boolean find = false;
        for(Book book1 : books){
            if(book1.getAuthors().equals(book.getAuthors())){
                book = book1;
                find = true;
                break;
            }
        }
        if(!find){
            booksRepository.save(book);
        }
        return book;
    }
}
