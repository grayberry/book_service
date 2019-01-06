package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Category;

import java.util.Set;

public interface CategoryService {
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    boolean addCategory(Category category);
    Set<Category> getCategoriesSet(String...categories);
}
