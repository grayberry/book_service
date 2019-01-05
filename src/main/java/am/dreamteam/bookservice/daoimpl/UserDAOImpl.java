package am.dreamteam.bookservice.daoimpl;

import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;


public class UserDAOImpl implements UserDAO {
    @Override
    public User findUserById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public boolean checkUser(String login) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession()
            TypedQuery<User> query = session.createQuery("from User where login=:login", User.class);
            query.setParameter("login", login);
            query.getSingleResult();
            return true;
        } catch (javax.persistence.NoResultException e){
            System.out.println("User not exist");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean regUser(User user) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession()
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String login, String password) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession()
            TypedQuery<User> query = session.createQuery("from User where login=:login and pass=:pass", User.class);
            query.setParameter("login", login);
            query.setParameter("pass", password);
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UsersAddBooks> showUserBooks(User user) {
        return findUserById(user.getId()).getUserBooks();
    }
}
