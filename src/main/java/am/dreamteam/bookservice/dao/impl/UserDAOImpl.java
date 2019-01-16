package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
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
    public String checkUser(String login) {
        String loginType;
        if(login.contains("@")) {
            loginType = "email";
        } else if(login.startsWith("+")) {
            loginType = "phone_number";
        } else
            loginType = "username";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){


            Query query = session.createNativeQuery("select 1 from login where " + loginType + " =?1");
            query.setParameter(1, login);

            if(query.getSingleResult() == null) {
                System.out.println(loginType + " is not correct");
                return null;
            }

            return loginType;
        } catch (NoResultException e){
          //  e.printStackTrace();
            System.out.println(loginType + " is not correct");
            return null;
        }
    }

    @Override
    public User regUser(String username, String pass, String email, String phoneNumber, String sex) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Query query = session.createNativeQuery("select 1 from users u where u.username = ?1");
            query.setParameter(1, username);

            if(!query.getResultList().isEmpty()) {
                System.out.println("Username is busy");
                return null;
            }

            query = session.createNativeQuery("select 1 from login l where l.email = ?1");
            query.setParameter(1, email);

            if(!query.getResultList().isEmpty()) {
                System.out.println("Email is busy");
                return null;
            }
            
            query = session.createNativeQuery("select 1 from login l where l.phone_number = ?1");
            query.setParameter(1, phoneNumber);

            if(!query.getResultList().isEmpty()) {
                System.out.println("phoneNumber is busy");
                return null;
            }

            User user = new User(username, sex);
            session.save(user);

            tx = session.getTransaction();
            tx.begin();
            query = session.createNativeQuery("insert into login(username, pass, email, phone_number) " +
                    "values(?1,?2,?3,?4)");

            query.setParameter(1, user.getUsername());
            query.setParameter(2, pass);
            query.setParameter(3, email);
            query.setParameter(4, phoneNumber);

            query.executeUpdate();
            tx.commit();

            return user;
        } catch (Throwable e){
            if(tx != null) {
                tx.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public User login(String login, String password, String loginType) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            loginType = checkUser(login);
            Query query = session.createNativeQuery("select username from login where " + loginType + " = ?1 and pass = ?2");

            query.setParameter(1, login);
            query.setParameter(2, password);

            String username;
            if((username = (String)query.getSingleResult()) == null) {
                System.out.println("Password is not correct");
                return null;
            }

            TypedQuery<User> typedQuery = session.createQuery("from User where username = :username", User.class);
            typedQuery.setParameter("username", username);

            return typedQuery.getSingleResult();

        } catch (javax.persistence.NoResultException e) {
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
