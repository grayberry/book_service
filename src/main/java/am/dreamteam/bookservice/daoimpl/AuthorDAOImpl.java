package am.dreamteam.bookservice.daoimpl;

import am.dreamteam.bookservice.dao.AuthorDAO;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public Author getAuthorById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Author.class, id);

    }

    @Override
    public Author getAuthorByName(String name) {
        return HibernateUtil.getSessionFactory().openSession().get(Author.class, name);
    }

    @Override
    public boolean addAuthor(Author author) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }
}
