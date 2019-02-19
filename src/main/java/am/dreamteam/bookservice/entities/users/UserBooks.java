package am.dreamteam.bookservice.entities.users;

import am.dreamteam.bookservice.entities.books.Book;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users_books")
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    @Column(name = "upload_date", nullable = false, columnDefinition = "DATE")
    private Date uploadDate;
    @Column(name = "is_removed", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isRemoved = false;
    @Column(name = "for_transfer", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean forTransfer = true;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    public UserBooks() {
    }

    public UserBooks(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\" : \"" + id +
                "\", \"user\" : \"" + user.getUsername() +
                "\", \"uploadDate\" : \"" + uploadDate +
                "\", \"book\" : " + book +
                "}";
    }
}
