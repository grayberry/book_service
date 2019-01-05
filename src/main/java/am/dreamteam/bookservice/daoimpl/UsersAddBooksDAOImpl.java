package am.dreamteam.bookservice.daoimpl;

import am.dreamteam.bookservice.dao.UsersAddBooksDAO;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class UsersAddBooksDAOImpl implements UsersAddBooksDAO {
    @Override
    public UsersAddBooks findUsersAddBooksById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(UsersAddBooks.class, id);
    }

    @Override
    public boolean addUsersAddBooks(UsersAddBooks usersAddBooks) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession()
            session.beginTransaction();
            session.save(usersAddBooks);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UsersAddBooks> getListUsersAddBooks() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            TypedQuery<UsersAddBooks> query = session.createQuery("from UsersAddBooks", UsersAddBooks.class);
            return query.getResultList();
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
