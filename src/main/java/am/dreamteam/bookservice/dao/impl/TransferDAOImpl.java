package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.TransferDAO;
import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UsersAddBooks;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

public class TransferDAOImpl implements TransferDAO {
    @Override
    public Transfer getTransferById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Transfer.class, id);
        } catch (javax.persistence.NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createTransfer(User from, User to, UsersAddBooks fromBook, UsersAddBooks toBook) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transfer transferFrom = new Transfer(from, to, fromBook);
            Transfer transferTo = new Transfer(to, from, toBook);
            fromBook.setUser(to);
            toBook.setUser(from);
            session.beginTransaction();
            session.save(transferFrom);
            session.save(transferTo);
            session.save(fromBook);
            session.save(toBook);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
