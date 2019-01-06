package am.dreamteam.bookservice.entities.users;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @Column(name = "transfer_date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id")
    private User userFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    private User userTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private UsersAddBooks usersAddBooks;

    public Transfer() {
    }

    public Transfer(User userFrom, User userTo, UsersAddBooks usersAddBooks) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.usersAddBooks = usersAddBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public UsersAddBooks getUsersAddBooks() {
        return usersAddBooks;
    }

    public void setUsersAddBooks(UsersAddBooks usersAddBooks) {
        this.usersAddBooks = usersAddBooks;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", date=" + date +
                ", userTo=" + userTo +
                ", userFrom=" + userFrom +
                ", usersAddBooks=" + usersAddBooks +
                '}';
    }
}
