package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.dao.CategoryDAO;
import am.dreamteam.bookservice.dao.impl.CategoryDAOImpl;
import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.service.CategoryService;

import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryByName(name);
    }

    @Override
    public boolean addCategory(Category category) {
        return categoryDAO.addCategory(category);
    }

    @Override
    public Set<Category> getCategoriesSet(String... categories) {
        return categoryDAO.getCategoriesSet(categories);
    }
}
