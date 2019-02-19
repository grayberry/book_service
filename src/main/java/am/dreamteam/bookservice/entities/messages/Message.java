package am.dreamteam.bookservice.entities.messages;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public int compareTo(Message message) {
        return getId().compareTo(message.getId());
    }

    @Override
    public String toString() {
        return "{ " +
                "\"id\" : \"" + id +
                "\", \"author\" : \"" + dialog.getUserFrom().getUsername() +
                "\", \"content\" : \"" + content +
                "\", \"isRead\" : \"" + isRead +
                "\" }";
    }
}
