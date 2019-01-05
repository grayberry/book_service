package am.dreamteam.bookservice.daoimpl;

import am.dreamteam.bookservice.dao.CategoryDAO;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public Category getCategoryById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Category.class, id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return HibernateUtil.getSessionFactory().openSession().get(Category.class, name);
    }

    @Override
    public boolean addCategory(Category category) {
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }
}
