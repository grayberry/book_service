package am.dreamteam.bookservice.daoimpl;

import am.dreamteam.bookservice.dao.BookDAO;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.entities.books.Book;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.Set;

public class BookDAOImpl implements BookDAO {
    @Override
    public Book getBookById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Book.class, id);
    }

    @Override
    public Book checkBookUnique(String title, Set<Author> authors, String language) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Book> query = session.createQuery("from Book where title=:title and language=:language", Book.class);
            query.setParameter("title", title);
            query.setParameter("language", language);
            Book book = query.getSingleResult();
            if(book.getAuthors().equals(authors)){
                return book;
            } else return null;

        } catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean addBook(Book book) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession()
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }
}
