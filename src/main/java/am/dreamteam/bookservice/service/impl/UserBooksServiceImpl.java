package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.repositories.BooksRepository;
import am.dreamteam.bookservice.repositories.UsersBooksRepository;
import am.dreamteam.bookservice.repositories.UsersRepository;
import am.dreamteam.bookservice.service.UserBooksService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBooksServiceImpl implements UserBooksService {

    private UsersBooksRepository usersBooksRepository;
    private UsersRepository usersRepository;
    private BooksRepository booksRepository;

    public UserBooksServiceImpl(UsersBooksRepository usersBooksRepository, UsersRepository usersRepository, BooksRepository booksRepository) {
        this.usersBooksRepository = usersBooksRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    @Override
    public Page<UserBooks> findAll(Pageable pageable) {
        return usersBooksRepository.findAll(pageable);
    }

    @Override
    public UserBooks findUserBookById(int id) {
        return usersBooksRepository.findById(id).get();
    }

    @Override
    public Page<UserBooks> findAllUserBooksByUserId(int id, Pageable pageable) {

        return usersBooksRepository.findAllByUser(usersRepository.findById(id).get(), pageable);
    }

    @Override
    @Cacheable("books")
    public Page<UserBooks> findAllUsersBooks(Pageable pageable) {
        return usersBooksRepository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "books", allEntries = true)
    public boolean addUserBooks(String username, List<Book> books) {
        User user = usersRepository.findUserByUsername(username);
        for (Book b : books) {
            UserBooks userBooks = new UserBooks(user,b);
            usersBooksRepository.save(userBooks);
        }
        return true;
    }

    @Override
    public List<UserBooks> findAllUserBooksByUsername(String username) {
       return usersBooksRepository.findAllByUser(usersRepository.findUserByUsername(username));
    }
    @Override
    public List<UserBooks> searchByTitle(String term) {
        return usersBooksRepository.searchByTitle(term);
    }

    @Override
    public List<UserBooks> searchByAuthor(String term) {
        return usersBooksRepository.searchByAuthor(term);
    }

}
