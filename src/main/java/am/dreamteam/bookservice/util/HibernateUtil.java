package am.dreamteam.bookservice.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = buildSession();

    private static SessionFactory buildSession(){
        final SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }

    public static void shutDown(){
        getSessionFactory().close();
    }
}
