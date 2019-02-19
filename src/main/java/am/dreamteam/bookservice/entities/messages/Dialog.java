package am.dreamteam.bookservice.entities.messages;

import am.dreamteam.bookservice.entities.users.User;

import javax.persistence.*;

@Entity
@Table(name = "dialogs")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    private User userTo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private User userFrom;

    public Dialog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "{ " +
                "\"id\" : \"" + id +
                "\", \"to\" : \"" + userTo +
                "\", \"author\" : \"" + userFrom +
                "\" }";
    }
}
