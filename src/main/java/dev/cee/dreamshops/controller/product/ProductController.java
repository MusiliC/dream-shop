package dev.cee.dreamshops.controller.product;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import dev.cee.dreamshops.controller.response.ApiResponse;
import dev.cee.dreamshops.dtos.AddProductDto;
import dev.cee.dreamshops.dtos.ProductUpdateRequestDto;
import dev.cee.dreamshops.exceptions.AlreadyExistException;
import dev.cee.dreamshops.exceptions.ResourceNotFoundException;
import dev.cee.dreamshops.model.Product;
import dev.cee.dreamshops.service.products.IproductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IproductService productService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllProducts() {

        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {

        try {
            Product foundProduct = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success", foundProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductDto product) {

        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Success", savedProduct));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequestDto product) {

        try {
            Product foundProduct = productService.updateProduct(product, id);
            return ResponseEntity.ok(new ApiResponse("Success", foundProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {

        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("brand-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {

        try {
            List<Product> products = productService.getProductByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("count-brand-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {

        try {
            var products = productService.countProductsByBrandAndName(brand, name);


            return ResponseEntity.ok(new ApiResponse("Product Count", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("category-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name) {

        try {
            List<Product> products = productService.getProductByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {

        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
