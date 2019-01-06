package am.dreamteam.bookservice.entities.users;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNimber;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tips")
    private int tips;
    @Column(name = "rating")
    private int rating;

    @CreationTimestamp
    @Column(name = "reg_date")
    private Date regDate;
    @Column(name = "remove")
    private boolean remove;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsersAddBooks> userBooks;

    @OneToMany(mappedBy = "userTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transfer> transfersTo;

    @OneToMany(mappedBy = "userFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transfer> transfersFrom;

    public User() {
    }

    public User(String login, String pass, String email, String phoneNimber, String sex) {
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.phoneNimber = phoneNimber;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNimber() {
        return phoneNimber;
    }

    public void setPhoneNimber(String phoneNimber) {
        this.phoneNimber = phoneNimber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTips() {
        return tips;
    }

    public void setTips(int tips) {
        this.tips = tips;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<UsersAddBooks> getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(List<UsersAddBooks> userBooks) {
        this.userBooks = userBooks;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public List<Transfer> getTransfersTo() {
        return transfersTo;
    }

    public void setTransfersTo(List<Transfer> transfersTo) {
        this.transfersTo = transfersTo;
    }

    public List<Transfer> getTransfersFrom() {
        return transfersFrom;
    }

    public void setTransfersFrom(List<Transfer> transfersFrom) {
        this.transfersFrom = transfersFrom;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNimber='" + phoneNimber + '\'' +
                ", sex='" + sex + '\'' +
                ", tips=" + tips +
                ", rating=" + rating +
                ", regDate=" + regDate +
                ", userBooks=" + userBooks +
                '}';
    }
}