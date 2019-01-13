package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.UsersBooksDAO;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class UsersBooksDAOImpl implements UsersBooksDAO {
    @Override
    public UserBooks getUsersBooksById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(UserBooks.class, id);
        }catch (javax.persistence.NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addUsersBooks(UserBooks usersBooks) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(usersBooks);
            session.getTransaction().commit();
            System.out.println("user book has added");
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserBooks> getUsersBooksList() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<UserBooks> query = session.createQuery("from UserBooks", UserBooks.class);
            return query.getResultList();
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
