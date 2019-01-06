package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.AuthorDAO;
import am.dreamteam.bookservice.entities.books.Author;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public Author getAuthorById(int id) {
        return HibernateUtil.getSession().get(Author.class, id);

    }

    @Override
    public Author getAuthorByName(String name) {
        try {
            Session session = HibernateUtil.getSession();
            TypedQuery<Author> query = session.createQuery("from Author where full_name=:name", Author.class);
            query.setParameter("name", name);
            Author author = query.getSingleResult();
            return author;
        } catch (javax.persistence.NoResultException e){
           // e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addAuthor(Author author) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
            System.out.println("author added");
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<Author> getAuthorsList(String... fullName) {
        Set<Author> authors = new HashSet<>();
        Author author;
        for(String name: fullName){
            if((author = getAuthorByName(name))==null){
                author = new Author(name);
                addAuthor(author);
            }
            authors.add(author);
        }
        return authors;
    }
}
