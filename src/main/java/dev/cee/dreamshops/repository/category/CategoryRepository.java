package dev.cee.dreamshops.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
