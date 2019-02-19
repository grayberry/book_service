package am.dreamteam.bookservice.service;

import am.dreamteam.bookservice.entities.books.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void addCategories(Set<Category> categories);
    Set<Category> getCategoriesSet(List list);
}
