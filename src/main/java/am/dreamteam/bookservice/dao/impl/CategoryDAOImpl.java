package am.dreamteam.bookservice.dao.impl;

import am.dreamteam.bookservice.dao.CategoryDAO;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.util.HibernateUtil;
import org.hibernate.Session;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public Category getCategoryById(int id) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Category.class, id);
        }catch (javax.persistence.NoResultException e){
             e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category getCategoryByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            TypedQuery<Category> query = session.createQuery("from Category where category=:name", Category.class);
            query.setParameter("name", name);
            Category category = query.getSingleResult();
            return category;
        } catch (javax.persistence.NoResultException e){
           // e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addCategory(Category category) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            System.out.println("category added");
            return true;
        } catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Set<Category> getCategoriesSet(String... categories) {
        Set<Category> categoriesSet = new HashSet<>();
        Category category;
        for(String c :categories){
            if((category = getCategoryByName(c))==null){
                category = new Category(c);
                addCategory(category);
            }
            categoriesSet.add(category);
        }
        return categoriesSet;
    }
}
