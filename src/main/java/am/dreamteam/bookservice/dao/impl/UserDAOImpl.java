package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.UserDAO;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;


public class UserDAOImpl implements UserDAO {
    @Override
    public User getUserById(int id) {
        return HibernateUtil.getSession().get(User.class, id);
    }

    @Override
    public boolean checkUser(String login) {
        try{
            Session session = HibernateUtil.getSession();
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
    public boolean regUser(User user) {

        try{
            Session session = HibernateUtil.getSession();
            TypedQuery<User> query = session.createQuery("from User where phone_number=:number or email=:email", User.class);
            query.setParameter("number", user.getPhoneNimber());
            query.setParameter("email", user.getEmail());
            List<User> check = query.getResultList();
            if(!check.isEmpty()){
                return false;
            }

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("user is registered");
            return true;
        } catch (org.hibernate.exception.ConstraintViolationException e){
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String login, String password) {
        try{
            Session session = HibernateUtil.getSession();
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
        try{
            Session session = HibernateUtil.getSession();
            TypedQuery<User> query = session.createQuery("from User", User.class);
            return query.getResultList();
        } catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UsersAddBooks> getUserBooks(User user) {
        try {
            Session session = HibernateUtil.getSession();
            TypedQuery<UsersAddBooks> query = session.createQuery("from UsersAddBooks where user_id=:id", UsersAddBooks.class);
            query.setParameter("id", user.getId());
            return query.getResultList();
        } catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
