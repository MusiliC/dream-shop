package dev.cee.dreamshops.repository.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String category, String brandName);

    List<Product> findByName(String productName);

    List<Product> findByBrandAndName(String brandName, String productName);

    Long countByBrandAndName(String brandName, String productName);

    boolean existsByNameAndBrand(String name, String brand);
}
