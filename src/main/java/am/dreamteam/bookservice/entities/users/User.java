package am.dreamteam.bookservice.entities.users;

import am.dreamteam.bookservice.domain.Role;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;
    @Column(name = "sex", columnDefinition = "VARCHAR(255)", nullable = false)
    private String sex;

    @Column(name = "email", columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "altername", columnDefinition = "VARCHAR(255)")
    private String altername;
    @Column(name = "photo", columnDefinition = "VARCHAR(1000)")
    private String photo;
    @Column(name = "tips", columnDefinition = "INTEGER DEFAULT 0")
    private Integer tips;
    @Column(name = "rating", columnDefinition = "INTEGER DEFAULT 0")
    private Integer rating;
    @CreationTimestamp
    @Column(name = "reg_date", nullable = false, columnDefinition = "DATE")
    private Date regDate;
    @Column(name = "active", columnDefinition = "BOOLEAN")
    private Boolean active;
    @Column(name = "activation_code")
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User() {
    }

    public User(String username, String password, String sex) {
        this.username = username;
        this.password = password;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void isActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}