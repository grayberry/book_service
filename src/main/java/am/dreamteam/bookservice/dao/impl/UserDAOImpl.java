package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.util.List;


public class UserDAOImpl implements UserDAO {
    @Override
    public User getUserById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(User.class, id);
        }catch (javax.persistence.NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkUser(String login) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            TypedQuery<User> query = session.createQuery("from User where login=:login", User.class);
            query.setParameter("login", login);
            query.getSingleResult();
            return true;
        } catch (javax.persistence.NoResultException e){
          //  e.printStackTrace();
            return false;
        }

    }

    @Override
    public User regUser(String username, String pass, String email, String phoneNumber, String sex) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query query = session.createQuery("select 1 from users u where u.username = :username");
            query.setParameter("username", username);

            if(!query.getResultList().isEmpty()) {
                System.out.println("Username is busy");
                return null;
            }

            query = session.createQuery("select 1 from login l where l.email = :email");
            query.setParameter("email", email);

            if(!query.getResultList().isEmpty()) {
                System.out.println("Email is busy");
                return null;
            }
            
            query = session.createQuery("select 1 from login l where l.phone_number = :phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);

            if(!query.getResultList().isEmpty()) {
                System.out.println("phoneNumber is busy");
                return null;
            }

            User user = new User(username, sex);
            session.save(user);

            query = session.createQuery("insert into login(user_id, pass, email, phone_number) " +
                    "values(:userId,:pass,:email,:phone_number");

            query.setParameter("userId", user.getId());
            query.setParameter("pass", pass);
            query.setParameter("email", email);
            query.setParameter("phone_number", phoneNumber);

            return user;
        } catch (ConstraintViolationException e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public User login(String login, String password) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            TypedQuery<User> query = session.createQuery("from User where login=:login and pass=:pass", User.class);
            query.setParameter("login", login);
            query.setParameter("pass", password);
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e){
           // e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<User> getAllUsersList(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            TypedQuery<User> query = session.createQuery("from User", User.class);
            return query.getResultList();
        } catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserBooks> getUserBooks(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){

            TypedQuery<UserBooks> query = session.createQuery("from UserBooks where user_id=:id", UserBooks.class);
            query.setParameter("id", user.getId());
            return query.getResultList();
        } catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
