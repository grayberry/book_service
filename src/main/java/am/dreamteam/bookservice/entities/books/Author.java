package am.dreamteam.bookservice.entities.books;

import javax.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String fullName;

    public Author() {
    }

    public Author(String fullName) {
        this.fullName = fullName.replaceAll("\"","'");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.replaceAll("\"","'");
    }

    @Override
    public String toString() {
        return "\"" + fullName + "\"";
    }
}
