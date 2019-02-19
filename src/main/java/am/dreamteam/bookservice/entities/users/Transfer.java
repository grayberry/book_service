package am.dreamteam.bookservice.entities.users;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(name = "transfer_date", columnDefinition = "DATE")
    private Date date;
    @Column(name = "done")
    private Boolean done = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private User userFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    private User userTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private UserBooks userBooks;

    public Transfer() {
    }

    public Transfer(User userFrom, User userTo, UserBooks usersAddBooks) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.userBooks = usersAddBooks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public UserBooks getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(UserBooks userBooks) {
        this.userBooks = userBooks;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", date=" + date +
                ", userTo=" + userTo +
                ", userFrom=" + userFrom +
                ", userBooks=" + userBooks +
                '}';
    }
}
