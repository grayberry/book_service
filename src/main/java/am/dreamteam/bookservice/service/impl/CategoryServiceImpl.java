package am.dreamteam.bookservice.service.impl;

import am.dreamteam.bookservice.entities.books.Category;
import am.dreamteam.bookservice.repositories.CategoriesRepository;
import am.dreamteam.bookservice.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public void addCategories(Set<Category> categories) {
        for(Category category: categories){
            categoriesRepository.save(category);
        }
    }

    @Override
    public Set<Category> getCategoriesSet(List list) {
        Set<Category> categories = new HashSet<>();
        Category category;
        for(Object c : list){
            if((category = categoriesRepository.findCategoriesByCategory(c.toString()))==null){
                category = new Category(c.toString());
                categoriesRepository.save(category);
            }
            categories.add(category);
        }
        return categories;
    }
}
