package am.dreamteam.bookservice.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final Session SESSION = buildSession();

    private static Session buildSession(){
        final SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
        final Session session = sessionFactory.openSession();
        return session;
    }

    public static Session getSession(){
        return SESSION;
    }

    public static void shutDown(){
        getSession().close();
    }
}
