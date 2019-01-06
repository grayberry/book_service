package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.BookDAO;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;


public class BookDAOImpl implements BookDAO {
    @Override
    public Book getBookById(int id) {
        return HibernateUtil.getSession().get(Book.class, id);
    }

    @Override
    public Book checkBookUnique(String title, String language, Set<Author> authors) {

        try{
            Session session = HibernateUtil.getSession();
            TypedQuery<Book> query = session.createQuery("from Book where title=:title and language=:language", Book.class);
            query.setParameter("title", title);
            query.setParameter("language", language);

            Book book = null;
            List<Book> books = query.getResultList();
            loop: for(Book b : books){
                if(b.getAuthors().equals(authors)){
                    System.out.println(b.getAuthors() + " : " + authors);
                    book = b;
                    break loop;
                }
            }
            return book;
        } catch (javax.persistence.NoResultException  e){
           // e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean addBook(Book book, Set<Author> authors, Set<Category>categories) {
        book.setAuthors(authors);
        book.setCategories(categories);
        try{
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
            System.out.println("book added");
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }
}