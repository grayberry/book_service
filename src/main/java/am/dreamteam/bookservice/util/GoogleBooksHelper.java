package am.dreamteam.bookservice.util;

import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.service.AuthorService;
import am.dreamteam.bookservice.service.BookService;
import am.dreamteam.bookservice.service.CategoryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GoogleBooksHelper {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;

    public GoogleBooksHelper(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    public JSONArray parseStringToJsonArray(String jsonString){
        return new JSONArray(jsonString);
    }

    public List<Book> parseJsonToBook(String jsonString){
        String noCoverImage = "/images/no_cover.jpg";
        List<Book> books = new ArrayList<>();

        JSONArray jsonArray = parseStringToJsonArray(jsonString);
        for(Object b : jsonArray){
            JSONObject jsonBook = (JSONObject) b;
            Book book  = new Book();
            book.setTitle(jsonBook.optString("title"));
            book.setLanguage(jsonBook.optString("language"));
            book.setPageCount(jsonBook.getInt("pageCount"));
            book.setDescription(jsonBook.optString("description"));
            Set<Author> authorSet = authorService.getAuthorsSet(
                    jsonBook.getJSONArray("authors").toList()
            );
            Set<Category> categorySet = categoryService.getCategoriesSet(
                    jsonBook.getJSONArray("categories").toList()
            );

            book.setImageRef(jsonBook.optString("image"));
            book.setIsbn13(jsonBook.optString("isbn13"));
            book.setIsbn10(jsonBook.optString("isbn10"));

            book.setAuthors(authorSet);
            book.setCategories(categorySet);

            book = bookService.checkBookUnique(book);
            books.add(book);
        }
        return books;
    }
}