package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.books.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    Category findCategoriesByCategory(String category);
}
