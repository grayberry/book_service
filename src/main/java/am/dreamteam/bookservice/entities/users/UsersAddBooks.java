package am.dreamteam.bookservice.entities.users;

import am.dreamteam.bookservice.entities.books.Book;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users_add_books")
public class UsersAddBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    @Column(name = "upload_date")
    private Date uploadDate;
    @Column(name = "remove")
    private boolean remove;
    @Column(name = "change")
    private boolean change;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public UsersAddBooks() {
    }

    public UsersAddBooks(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "UsersAddBooks{" +
                "id=" + id +
                ", user: " + user.getLogin() +
                ", uploadDate=" + uploadDate +
                ", book=" + book +
                '}';
    }
}
