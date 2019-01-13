package am.dreamteam.bookservice.entities.users;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "altername")
    private String altername;
    @Column(name = "sex")
    private String sex;
    @Column(name = "photo")
    private String photo;
    @Column(name = "tips")
    private int tips;
    @Column(name = "rating")
    private int rating;

    @CreationTimestamp
    @Column(name = "reg_date")
    private Date regDate;
    @Column(name = "isRemove")
    private boolean isRemove;

    public User() {
    }

    public User(String username, String sex) {
        this.username = username;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAltername() {
        return altername;
    }

    public void setAltername(String altername) {
        this.altername = altername;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }
}