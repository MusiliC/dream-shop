package dev.cee.dreamshops.service.products;

import java.util.List;

import dev.cee.dreamshops.dtos.AddProductDto;
import dev.cee.dreamshops.dtos.ProductResponseDto;
import dev.cee.dreamshops.dtos.ProductUpdateRequestDto;
import dev.cee.dreamshops.model.Product;

public interface IproductService {

    Product addProduct(AddProductDto request);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequestDto productUpdateRequestDtoduct, Long productId);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String category, String brandName);
    List<Product> getProductByName(String productName);
    List<Product> getProductByBrandAndName(String brandName, String productName);
    Long countProductsByBrandAndName(String brandName, String productName);

    List<ProductResponseDto> getConvertedProducts(List<Product> products);

    ProductResponseDto convertToDto(Product product);
}
