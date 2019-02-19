package am.dreamteam.bookservice.entities.books;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "category", nullable = false, columnDefinition = "VARCHAR(255)")
    private String category;

    public Category() {
    }

    public Category(String category) {
        this.category = category.replaceAll("\"","'");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category.replaceAll("\"","'");
    }

    @Override
    public String toString() {
        return "\"" + category + "\"";
    }
}
