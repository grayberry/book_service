package am.dreamteam.bookservice.entities.books;

import am.dreamteam.bookservice.entities.users.UsersAddBooks;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "language", nullable = false, columnDefinition = "VARCHAR(255")
    private String language;
    @Column(name = "page_count")
    private int pageCount;
    @Column(name = "image_ref")
    private String imageRef;
    @Column(name = "description")
    private String description;
    @Column(name = "rating", nullable = false, columnDefinition = "integer default 0")
    private int rating;
    @Column(name = "isbn_10")
    private String isbn10;
    @Column(name = "isbn_13")
    private String isbn13;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name ="author_id"))
    private Set<Author> authors;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "book_categories", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name ="category_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<UsersAddBooks> usersAddBooks;

    public Book() {
    }

    public Book(String title, String language, int pageCount, String imageRef, String description, String isbn10, String isbn13) {
        this.title = title;
        this.language = language;
        this.pageCount = pageCount;
        this.imageRef = imageRef;
        this.description = description;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<UsersAddBooks> getUsersAddBooks() {
        return usersAddBooks;
    }

    public void setUsersAddBooks(List<UsersAddBooks> usersAddBooks) {
        this.usersAddBooks = usersAddBooks;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", categories=" + categories +
                ", language='" + language + '\'' +
                ", pageCount=" + pageCount +
                ", imageRef='" + imageRef + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                '}';
    }
}
